package com.door43.translationstudio.dialogs;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.door43.translationstudio.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

/**
 * Created by blm on 11/28/15.
 * The intent of this is to create an AlertDialog replacement that has a modern UI appearance even
 *     on older devices.  It also has support for HTML content.
 */
public class CustomAlertDialog extends DialogFragment {

    private int mMessageID = 0;
    private int mMessageHtmlID = 0;
    private int mTitleID = 0;
    private Button mPositiveButton;
    private Button mNegativeButton;
    private Activity mContext;
    private View.OnClickListener mPositiveListener;
    private int mPositiveTextID = 0;
    private View.OnClickListener mNegativeListener;
    private int mNegativeTextID = 0;
    private CharSequence mTitle = null;
    private CharSequence mMessage = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_custom_alert, container, false);
        if (0 != mMessageID) {
            final HtmlTextView message = (HtmlTextView) rootView.findViewById(R.id.dialog_content);
            message.setText(mMessageID);
        }
        if (0 != mMessageHtmlID) {
            final HtmlTextView message = (HtmlTextView) rootView.findViewById(R.id.dialog_content);
            String mMessageHtml = getResources().getText(mMessageHtmlID).toString();
            message.setHtmlFromString(mMessageHtml, true);
        }
        if (mMessage != null) {
            final HtmlTextView message = (HtmlTextView) rootView.findViewById(R.id.dialog_content);
            message.setText(mMessage);
        }
        mPositiveButton = (Button) rootView.findViewById(R.id.confirmButton);
        if(0 != mPositiveTextID) {
            // convert string to upper case manually, because on older devices uppercase attribute
            // in UI
            String label = getResources().getText(mPositiveTextID).toString().toUpperCase();
            mPositiveButton.setText(label);
        } else {
            mPositiveButton.setVisibility(View.GONE);
        }

        if(null != mPositiveListener) {
            mPositiveButton.setOnClickListener(mPositiveListener);
        }
        else {
            mPositiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        mNegativeButton = (Button) rootView.findViewById(R.id.cancelButton);
        if(0 != mNegativeTextID) {
            // convert string to upper case manually, because on older devices uppercase attribute
            // in UI
            String label = getResources().getText(mNegativeTextID).toString().toUpperCase();
            mNegativeButton.setText(label);
        } else {
            mNegativeButton.setVisibility(View.GONE);
        }

        if(null != mNegativeListener) {
            mNegativeButton.setOnClickListener(mNegativeListener);
        }
        else {
            mNegativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }

        if (0 != mTitleID) {
            final TextView title = (TextView) rootView.findViewById(R.id.dialog_title);
            title.setText(mTitleID);
        }
        if(mTitle != null) {
            TextView title = (TextView) rootView.findViewById(R.id.dialog_title);
            title.setText(mTitle);
        }

        return rootView;
    }

    public CustomAlertDialog setContext(final Activity context) {
        mContext = context;
        return this;
    }

    public CustomAlertDialog setTitle(int textResId) {
        mTitleID = textResId;
        mTitle = null;
        return this;
    }

    public CustomAlertDialog setTitle(CharSequence text) {
        mTitle = text;
        mTitleID = 0;
        return this;
    }

    public CustomAlertDialog setMessage(int textResId) {
        mMessageID = textResId;
        mMessage = null;
        return this;
    }

    public CustomAlertDialog setMessage(CharSequence text) {
        mMessage = text;
        mMessageID = 0;
        mMessageHtmlID = 0;
        return this;
    }

    public CustomAlertDialog setMessageHtml(int textResId) {
        mMessageHtmlID = textResId;
        mMessage = null;
        return this;
    }

    public void show(final String tag) {
        FragmentManager fm = mContext.getFragmentManager();
        show(fm, tag);
    }

    public CustomAlertDialog setPositiveButton(int textResId, View.OnClickListener l) {
        mPositiveListener = l;
        mPositiveTextID = textResId;
        return this;
    }

    public CustomAlertDialog setNegativeButton(int textResId, View.OnClickListener l) {
        mNegativeListener = l;
        mNegativeTextID = textResId;
        return this;
    }

    static public CustomAlertDialog Create(final Activity context) {
        CustomAlertDialog dlg = new CustomAlertDialog();
        dlg.setContext(context);
        return dlg;
    }

}