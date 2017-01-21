package com.susion.boring.music.itemhandler;

import android.text.TextUtils;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.susion.boring.R;
import com.susion.boring.base.SimpleItemHandler;
import com.susion.boring.base.ViewHolder;
import com.susion.boring.music.PlayMusicActivity;
import com.susion.boring.music.model.Song;
import com.susion.boring.utils.ToastUtils;

/**
 * Created by susion on 17/1/20.
 */
public class SearchMusicResultIH extends SimpleItemHandler<Song>{

    @Override
    public void onBindDataView(ViewHolder vh, Song data, int position) {

        vh.getTextView(R.id.item_music_search_tv_song_name).setText(data.name);

        if (!data.artists.isEmpty()) {
            vh.getTextView(R.id.item_music_search_tv_art_album).setText(data.artists.get(0).name+"-"+data.album.name);
        }
        SimpleDraweeView ivPic = vh.get(R.id.item_music_search_siv_pic);
        ivPic.setImageURI(data.album.picUrl);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.item_music_search_music_result;
    }

    @Override
    public void onClick(View view) {
        if (TextUtils.isEmpty(mData.audio)) {
            ToastUtils.showShort("抱歉啦! 暂时没有播放资源");
        }

        PlayMusicActivity.start(mContext, mData);

    }
}