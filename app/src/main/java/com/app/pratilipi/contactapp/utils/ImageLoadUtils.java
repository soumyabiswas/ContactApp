package com.app.pratilipi.contactapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.app.pratilipi.contactapp.R;
import com.travelyaari.tycorelib.ultlils.CoreLogger;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

/**
 * Created by triode on 11/8/16.
 */
public final class ImageLoadUtils {

    /**
     * function initiate the loading of the provide image urls
     *
     * @param mLocalContext the context which the image to be loaded
     *                      {@link Glide#with(android.app.Fragment)}
     * @param url           the url lto be loaded
     * @param target        the target image
     */
    public static final Target<Drawable> loadWith(final Fragment mLocalContext,
                                                  final String url, final ImageView target) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH);
        return Glide.with(mLocalContext)
                .load(url)
                .apply(options)
                .into(target);
    }

    /**
     * function initiate the loading of the provide image urls
     *
     * @param mLocalContext the context which the image to be loaded
     *                      {@link Glide#with(android.app.Fragment)}
     * @param url           the url lto be loaded
     * @param target        the target image
     */
    public static final Target<Drawable> loadWithWithDiskCatching(final Fragment mLocalContext,
                                                                  final String url, final ImageView target) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .skipMemoryCache(true)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.HIGH);

        return Glide.with(mLocalContext)
                .load(url)
                .apply(options)
                .into(target);
    }

    /**
     * function initiate the loading of the provide image urls
     *
     * @param mContext the context which the image to be loaded
     *                      {@link Glide#with(android.app.Fragment)}
     * @param url           the url lto be loaded
     * @param target        the target image
     */
    public static final Target<Drawable> loadWith(final Context mContext,
                                                  final String url, final ImageView target) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .priority(Priority.HIGH);
        return Glide.with(mContext)
                .load(url)
                .apply(options)
                .into(target);
    }

    /**
     * function initiate the loading of the provide image urls
     *
     * @param mContext the context which the image to be loaded
     *                 {@link Glide#with(android.app.Fragment)}
     * @param url      the url lto be loaded
     * @param target   the target image
     */
    public static final Target<Drawable> loadWithAnimation(final Context mContext,
                                                           final String url, final ImageView target) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .priority(Priority.HIGH);
        TransitionOptions transitionOptions = new DrawableTransitionOptions().crossFade(500);
        return Glide.with(mContext)
                .load(url)
                .apply(options)
                .transition(transitionOptions)
                .into(target);
    }

    /**
     * function initiate the loading of the provide image urls
     *
     * @param mContext the context which the image to be loaded
     *                      {@link Glide#with(android.app.Fragment)}
     * @param url           the url lto be loaded
     * @param target        the target image
     */
    public static final Target<Drawable> loadWithoutCenterCrop(final Context mContext,
                                                               final String url, final ImageView target) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .priority(Priority.HIGH);
        return Glide.with(mContext)
                .load(url)
                .apply(options)
                .into(target);
    }

    /**
     * function initiate the loading of the provide image urls
     *
     * @param mContext the context which the image to be loaded
     *                 {@link Glide#with(android.app.Fragment)}
     * @param url      the url lto be loaded
     * @param target   the target image
     */
    public static final Target<Drawable> loadWithFullWidth(final Context mContext, final String url,
                                                           final ImageView target, int defImageRes) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .priority(Priority.HIGH);
        return Glide.with(mContext)
                .load(url)
                .apply(options)
                .into(target);
    }

    /**
     * function initiate the loading of the provide image urls
     *
     * @param context the context which the image to be loaded
     *                {@link Glide#with(Context)}
     * @param url     the url lto be loaded
     * @param target  the target image
     */
    public static final Target loadWith(final Context context, final String url,
                                        final ImageView target, final int errorResource, final int placeHolderResource) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(AppCompatResources.getDrawable(context, placeHolderResource))
                .error(AppCompatResources.getDrawable(context, errorResource))
                .priority(Priority.HIGH);

        return Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }

    /**
     * function initiate the loading of the provide image urls
     *
     * @param context the context which the image to be loaded
     *                {@link Glide#with(Context)}
     * @param url     the url lto be loaded
     * @param target  the target image
     */
    public static final Target loadWithoutCenterCrop(final Context context, final String url,
                                                     final ImageView target, final int errorResource, final int placeHolderResource) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .placeholder(AppCompatResources.getDrawable(context, placeHolderResource))
                .error(AppCompatResources.getDrawable(context, errorResource))
                .fitCenter()
                .priority(Priority.HIGH);


        return Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }

    /**
     * function initiate the loading of the provide image urls
     *
     * @param context the context which the image to be loaded
     *                {@link Glide#with(Context)}
     * @param url     the url lto be loaded
     * @param target  the target image
     */
    public static final Target loadWithoutCenterCrop(final Context context, final String url,
                                                     final ImageView target, final Drawable errorResource, final Drawable placeHolderResource) {
        CoreLogger.log(ImageLoadUtils.class.getSimpleName(), url);
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .placeholder(placeHolderResource)
                .error(errorResource)
                .fitCenter()
                .priority(Priority.HIGH);

        return Glide.with(context)
                .load(url)
                .apply(options)
                .into(target);
    }


}
