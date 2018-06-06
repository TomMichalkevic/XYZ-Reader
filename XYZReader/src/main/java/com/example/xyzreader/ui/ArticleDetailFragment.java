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

//    public static ArticleDetailFragment newInstance(long itemId) {
//        Bundle arguments = new Bundle();
//        arguments.putLong(ARG_ITEM_ID, itemId);
//        ArticleDetailFragment fragment = new ArticleDetailFragment();
//        fragment.setArguments(arguments);
//        return fragment;
//    }

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

//        Log.i(TAG, "bindViews: " + (mCursor != null));
//        if (mCursor != null) {
//            Log.i(TAG, "bindViews: in BIND VIEWS");
////            mRootView.setAlpha(0);
//            mRootView.setVisibility(View.VISIBLE);
//            //mRootView.animate().alpha(1);
//            titleView.setText(mCursor.getString(ArticleLoader.Query.TITLE));
//            //Date publishedDate = parsePublishedDate();
//            bylineView.setText(mCursor.getString(ArticleLoader.Query.AUTHOR));
//            bodyView.setText(mCursor.getString(ArticleLoader.Query.BODY));
        detailsLayout.setTitle(mCursor.getString(ArticleLoader.Query.TITLE));
        titleView.setText(mCursor.getString(ArticleLoader.Query.AUTHOR));
        bodyView.setText(mCursor.getString(ArticleLoader.Query.BODY));
        Picasso.with(getContext()).load(mCursor.getString(ArticleLoader.Query.THUMB_URL)).into(mThumbImageView);
//        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return ArticleLoader.newInstanceForItemId(getContext(), Long.parseLong(uri.getLastPathSegment()));
        //return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mCursor = cursor;
        Log.i("TESTING", "onLoadFinished: " + mCursor.getCount());
        if (mCursor.getCount() > 0) {
            mCursor.moveToFirst();
            // TODO: optimize
//            while (!mCursor.isAfterLast()) {
//                if (mCursor.getLong(ArticleLoader.Query._ID) == mStartId) {
//                    final int position = mCursor.getPosition();
////                    mPager.setCurrentItem(position, false);
//                    break;
//                }
//                mCursor.moveToNext();
//            }
            Log.i("TESTING", "onLoadFinished: " + mCursor.getString(ArticleLoader.Query._ID));
            Log.i("TESTING", "onLoadFinished: " + mCursor.getString(ArticleLoader.Query.AUTHOR));
            Log.i("TESTING", "onLoadFinished: " + mCursor.getString(ArticleLoader.Query.ASPECT_RATIO));
            Log.i("TESTING", "onLoadFinished: " + mCursor.getString(ArticleLoader.Query.BODY));
            Log.i("TESTING", "onLoadFinished: " + mCursor.getString(ArticleLoader.Query.PHOTO_URL));
            Log.i("TESTING", "onLoadFinished: " + mCursor.getString(ArticleLoader.Query.PUBLISHED_DATE));
            Log.i("TESTING", "onLoadFinished: " + mCursor.getString(ArticleLoader.Query.THUMB_URL));
            Log.i("TESTING", "onLoadFinished: " + mCursor.getString(ArticleLoader.Query.TITLE));
            bindViews();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mCursor = null;
    }
}
