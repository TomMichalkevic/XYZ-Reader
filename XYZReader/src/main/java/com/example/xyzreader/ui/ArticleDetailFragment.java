package com.example.xyzreader.ui;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ShareCompat;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A fragment representing a single Article detail screen. This fragment is
 * either contained in a {@link ArticleListActivity} in two-pane mode (on
 * tablets) or a {@link ArticleDetailActivity} on handsets.
 */
public class ArticleDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "ArticleDetailFragment";
    private View mRootView;
    private Cursor mCursor;
    private Uri uri;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
    }

    public ArticleDetailActivity getActivityCast() {
        return (ArticleDetailActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_article_detail, container, false);
        Intent intent = getActivity().getIntent();
        if(intent != null){
            uri = intent.getData();
        }
        getLoaderManager().initLoader(0, null, this);
        if(getActivity().getIntent()!=null){
            Log.i("TESTING", "onCreateView: NOT NULL");
        }
        mRootView.findViewById(R.id.share_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(getActivity())
                        .setType("text/plain")
                        .setText(mCursor.getString(ArticleLoader.Query.TITLE) + " " + mCursor.getString(ArticleLoader.Query.AUTHOR))
                        .getIntent(), getString(R.string.action_share)));
            }
        });
        return mRootView;
    }


    private void bindViews() {
        if (mRootView == null) {
            return;
        }

        CollapsingToolbarLayout detailsLayout = mRootView.findViewById(R.id.collapsingDetails);
        TextView titleView = mRootView.findViewById(R.id.article_title);
        TextView bodyView = mRootView.findViewById(R.id.article_body);
        ImageView mThumbImageView = mRootView.findViewById(R.id.photo);


        bodyView.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "Rosario-Regular.ttf"));
        detailsLayout.setTitle(mCursor.getString(ArticleLoader.Query.TITLE));
        titleView.setText(mCursor.getString(ArticleLoader.Query.AUTHOR));
        bodyView.setText(mCursor.getString(ArticleLoader.Query.BODY));
        Picasso.with(getContext()).load(mCursor.getString(ArticleLoader.Query.THUMB_URL)).into(mThumbImageView);
//        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(getContext(), Long.parseLong(uri.getLastPathSegment()));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursor = cursor;
        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            bindViews();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
    }
}
