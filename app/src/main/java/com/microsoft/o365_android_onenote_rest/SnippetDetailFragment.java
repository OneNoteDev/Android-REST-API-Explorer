package com.microsoft.o365_android_onenote_rest;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.microsoft.AuthenticationManager;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.live.LiveAuthClient;
import com.microsoft.live.LiveAuthException;
import com.microsoft.live.LiveAuthListener;
import com.microsoft.live.LiveConnectSession;
import com.microsoft.live.LiveStatus;
import com.microsoft.o365_android_onenote_rest.snippet.AbstractSnippet;
import com.microsoft.o365_android_onenote_rest.snippet.Callback;
import com.microsoft.o365_android_onenote_rest.snippet.Input;
import com.microsoft.o365_android_onenote_rest.snippet.SnippetContent;
import com.microsoft.o365_android_onenote_rest.util.SharedPrefsUtil;
import com.microsoft.o365_android_onenote_rest.util.User;
import com.microsoft.onenotevos.BaseVO;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import retrofit.client.Header;
import retrofit.client.Response;
import timber.log.Timber;

import static android.R.layout.simple_spinner_dropdown_item;
import static android.R.layout.simple_spinner_item;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.microsoft.o365_android_onenote_rest.R.id.btn_launch_browser;
import static com.microsoft.o365_android_onenote_rest.R.id.btn_launch_onenote;
import static com.microsoft.o365_android_onenote_rest.R.id.btn_run;
import static com.microsoft.o365_android_onenote_rest.R.id.progressbar;
import static com.microsoft.o365_android_onenote_rest.R.id.spinner;
import static com.microsoft.o365_android_onenote_rest.R.id.txt_desc;
import static com.microsoft.o365_android_onenote_rest.R.id.txt_hyperlink;
import static com.microsoft.o365_android_onenote_rest.R.id.txt_input;
import static com.microsoft.o365_android_onenote_rest.R.id.txt_request_url;
import static com.microsoft.o365_android_onenote_rest.R.id.txt_response_body;
import static com.microsoft.o365_android_onenote_rest.R.id.txt_response_headers;
import static com.microsoft.o365_android_onenote_rest.R.id.txt_status_code;
import static com.microsoft.o365_android_onenote_rest.R.id.txt_status_color;
import static com.microsoft.o365_android_onenote_rest.R.string.clippy;
import static com.microsoft.o365_android_onenote_rest.R.string.req_url;
import static com.microsoft.o365_android_onenote_rest.R.string.response_body;
import static com.microsoft.o365_android_onenote_rest.R.string.response_headers;

public class SnippetDetailFragment<T, Result>
        extends BaseFragment
        implements Callback<Result>,
        AuthenticationCallback<AuthenticationResult>, LiveAuthListener {

    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_TEXT_INPUT = "TextInput";
    public static final String ARG_SPINNER_SELECTION = "SpinnerSelection";
    public static final int UNSET = -1;
    public static final String APP_STORE_URI = "https://play.google.com/store/apps/details?id=com.microsoft.office.onenote";

    @InjectView(txt_status_code)
    protected TextView mStatusCode;

    @InjectView(txt_status_color)
    protected View mStatusColor;

    @InjectView(txt_desc)
    protected TextView mSnippetDescription;

    @InjectView(txt_request_url)
    protected TextView mRequestUrl;

    @InjectView(txt_response_headers)
    protected TextView mResponseHeaders;

    @InjectView(txt_response_body)
    protected TextView mResponseBody;

    @InjectView(spinner)
    protected Spinner mSpinner;

    @InjectView(txt_input)
    protected EditText mEditText;

    @InjectView(progressbar)
    protected ProgressBar mProgressbar;

    @InjectView(btn_run)
    protected Button mRunButton;

    @Inject
    protected AuthenticationManager mAuthenticationManager;

    @Inject
    protected LiveAuthClient mLiveAuthClient;

    boolean setupDidRun = false;
    private AbstractSnippet<T, Result> mItem;

    public SnippetDetailFragment() {
    }

    @OnClick(txt_request_url)
    public void onRequestUrlClicked(TextView tv) {
        clipboard(tv);
    }

    @OnClick(txt_response_headers)
    public void onResponseHeadersClicked(TextView tv) {
        clipboard(tv);
    }

    @OnClick(txt_response_body)
    public void onResponseBodyClicked(TextView tv) {
        clipboard(tv);
    }

    @InjectView(btn_launch_onenote)
    protected Button mLaunchOneNote;

    @InjectView(btn_launch_browser)
    protected Button mLaunchBrowser;

    private void clipboard(TextView tv) {
        int which;
        switch (tv.getId()) {
            case txt_request_url:
                which = req_url;
                break;
            case txt_response_headers:
                which = response_headers;
                break;
            case txt_response_body:
                which = response_body;
                break;
            default:
                which = UNSET;
        }
        String what = which == UNSET ? "" : getString(which) + " ";
        what += getString(clippy);
        Toast.makeText(getActivity(), what, Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // old way
            ClipboardManager clipboardManager = (ClipboardManager)
                    getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(tv.getText());
        } else {
            clipboard11(tv);
        }
    }

    @TargetApi(11)
    private void clipboard11(TextView tv) {
        android.content.ClipboardManager clipboardManager =
                (android.content.ClipboardManager) getActivity()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("OneNote", tv.getText());
        clipboardManager.setPrimaryClip(clipData);
    }

    @OnClick(btn_run)
    public void onRunClicked(Button btn) {
        mRequestUrl.setText("");
        mResponseHeaders.setText("");
        mResponseBody.setText("");
        displayStatusCode("", getResources().getColor(R.color.transparent));
        mProgressbar.setVisibility(VISIBLE);
        mItem.request(mItem.mService, this);
    }

    @OnClick(txt_hyperlink)
    public void onDocsLinkClicked(TextView textView) {
        launchUri(Uri.parse(mItem.getUrl()));
    }

    private void launchUri(Uri uri) {
        Intent launchOneNoteExtern = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(launchOneNoteExtern);
        } catch (ActivityNotFoundException e) {
            launchOneNoteExtern = new Intent(Intent.ACTION_VIEW, Uri.parse(APP_STORE_URI));
            startActivity(launchOneNoteExtern);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = (AbstractSnippet<T, Result>)
                    SnippetContent.ITEMS.get(getArguments().getInt(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_snippet_detail, container, false);
        ButterKnife.inject(this, rootView);
        mSnippetDescription.setText(mItem.getDescription());
        if (Input.Spinner == mItem.mInputArgs) {
            mSpinner.setVisibility(VISIBLE);
        } else if (Input.Text == mItem.mInputArgs) {
            mEditText.setVisibility(VISIBLE);
        } else if (Input.Both == mItem.mInputArgs) {
            mSpinner.setVisibility(VISIBLE);
            mEditText.setVisibility(VISIBLE);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (null != getActivity() && getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (null != activity.getSupportActionBar()) {
                activity.getSupportActionBar().setTitle(mItem.getName());
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (User.isOrg()) {
            mAuthenticationManager.connect(this);
        } else if (User.isMsa()) {
            mLiveAuthClient.loginSilent(BaseActivity.sSCOPES, this);
        }
    }

    private retrofit.Callback<String[]> getSetUpCallback() {
        return new retrofit.Callback<String[]>() {
            @Override
            public void success(String[] strings, Response response) {
                mProgressbar.setVisibility(View.GONE);
                if (isAdded() && strings.length > 0) {
                    populateSpinner(strings);
                    mRunButton.setEnabled(true);
                } else if (isAdded() && strings.length <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.err_setup)
                            .setMessage(R.string.err_setup_msg)
                            .setPositiveButton(R.string.dismiss, null)
                            .show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (isAdded()) {
                    displayThrowable(error.getCause());
                    mProgressbar.setVisibility(View.GONE);
                }
            }
        };
    }

    private void populateSpinner(String[] strings) {
        ArrayAdapter<String> spinnerArrayAdapter
                = new ArrayAdapter<>(
                getActivity(),
                simple_spinner_item,
                strings);
        spinnerArrayAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void success(Result result, Response response) {
        if (!isAdded()) {
            // the user has left...
            return;
        }
        mProgressbar.setVisibility(GONE);
        displayResponse(response);
        maybeShowQuickLink(result);
    }

    private void maybeShowQuickLink(Result result) {
        if (result instanceof BaseVO) {
            final BaseVO vo = (BaseVO) result;
            if (hasOneNoteClientLink(vo)) {
                showOneNoteLaunchBtn(vo);
            }
            if (hasWebClientLink(vo)) {
                showBrowserLaunchBtn(vo);
            }
        }
    }

    private boolean hasWebClientLink(BaseVO vo) {
        return null != vo.links && null != vo.links.oneNoteWebUrl;
    }

    private boolean hasOneNoteClientLink(BaseVO vo) {
        return null != vo.links && null != vo.links.oneNoteClientUrl;
    }

    private void showBrowserLaunchBtn(final BaseVO vo) {
        mLaunchBrowser.setEnabled(true);
        mLaunchBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchUri(Uri.parse(vo.links.oneNoteWebUrl.href));
            }
        });
    }

    private void showOneNoteLaunchBtn(final BaseVO page) {
        mLaunchOneNote.setEnabled(true);
        mLaunchOneNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchUri(Uri.parse(page.links.oneNoteClientUrl.href));
            }
        });
    }

    private void displayResponse(Response response) {
        int color = getColor(response);
        displayStatusCode(Integer.valueOf(response.getStatus())
                .toString(), getResources().getColor(color));
        displayRequestUrl(response);
        maybeDisplayResponseHeaders(response);
        maybeDisplayResponseBody(response);
    }

    private void maybeDisplayResponseBody(Response response) {
        if (null != response.getBody()) {
            String body = null;
            InputStream is = null;
            try {
                is = response.getBody().in();
                body = IOUtils.toString(is);
                String formattedJson = new JSONObject(body).toString(2);
                formattedJson = StringEscapeUtils.unescapeJson(formattedJson);
                mResponseBody.setText(formattedJson);
            } catch (JSONException e) {
                if (null != body) {
                    // body wasn't JSON
                    mResponseBody.setText(body);
                } else {
                    // set the stack trace as the response body
                    displayThrowable(e);
                }
            } catch (IOException e) {
                e.printStackTrace();
                displayThrowable(e);
            } finally {
                if (null != is) {
                    IOUtils.closeQuietly(is);
                }
            }
        }
    }

    private void maybeDisplayResponseHeaders(Response response) {
        if (null != response.getHeaders()) {
            List<Header> headers = response.getHeaders();
            String headerText = "";
            for (Header header : headers) {
                headerText += header.getName() + " : " + header.getValue() + "\n";
            }
            mResponseHeaders.setText(headerText);
        }
    }

    private void displayRequestUrl(Response response) {
        String requestUrl = response.getUrl();
        mRequestUrl.setText(requestUrl);
    }

    private void displayStatusCode(String text, int color) {
        mStatusCode.setText(text);
        mStatusColor.setBackgroundColor(color);
    }

    private void displayThrowable(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        String trace = sw.toString();
        mResponseBody.setText(trace);
    }

    private int getColor(Response response) {
        int color;
        switch (response.getStatus() / 100) {
            case 1:
            case 2:
                color = R.color.code_1xx;
                break;
            case 3:
                color = R.color.code_3xx;
                break;
            case 4:
            case 5:
                color = R.color.code_4xx;
                break;
            default:
                color = R.color.transparent;
        }
        return color;
    }

    @Override
    public void failure(RetrofitError error) {
        Timber.d(error, "");
        mProgressbar.setVisibility(GONE);
        if (null != error.getResponse()) {
            displayResponse(error.getResponse());
        }
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> args = new HashMap<>();
        if (Input.Spinner == mItem.mInputArgs) {
            args.put(ARG_SPINNER_SELECTION, mSpinner.getSelectedItem().toString());
        } else if (Input.Text == mItem.mInputArgs) {
            args.put(ARG_TEXT_INPUT, mEditText.getText().toString());
        } else if (Input.Both == mItem.mInputArgs) {
            args.put(ARG_SPINNER_SELECTION, mSpinner.getSelectedItem().toString());
            args.put(ARG_TEXT_INPUT, mEditText.getText().toString());
        } else {
            throw new IllegalStateException("No input modifier to match type");
        }
        return args;
    }

    @Override
    public void onSuccess(AuthenticationResult authenticationResult) {
        SharedPrefsUtil.persistAuthToken(authenticationResult);
        ready();
    }

    private void ready() {
        if (Input.None == mItem.mInputArgs) {
            mRunButton.setEnabled(true);
        } else if (!setupDidRun) {
            setupDidRun = true;
            mProgressbar.setVisibility(View.VISIBLE);
            mItem.setUp(AbstractSnippet.sServices, getSetUpCallback());
        }
    }

    @Override
    public void onError(Exception e) {
        if (!isAdded()) {
            return;
        }
        displayThrowable(e);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.token_err_title)
                .setMessage(R.string.token_err_msg)
                .setPositiveButton(R.string.dismiss, null)
                .setNegativeButton(R.string.disconnect, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuthenticationManager.disconnect();
                    }
                }).show();
    }

    @Override
    public void onAuthComplete(LiveStatus status, LiveConnectSession session, Object userState) {
        if (null != session) {
            SharedPrefsUtil.persistAuthToken(session);
        }
        ready();
    }

    @Override
    public void onAuthError(LiveAuthException exception, Object userState) {
        onError(exception);
    }
}