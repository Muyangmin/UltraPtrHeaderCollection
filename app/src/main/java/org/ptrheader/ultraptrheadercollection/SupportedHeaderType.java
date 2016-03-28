package org.ptrheader.ultraptrheadercollection;

/**
 * This is just a convenience interface. Constant interface is not much encouraged.
 */
public interface SupportedHeaderType {
    /**
     * @see org.ptrheader.library.ballslogan.BallSloganHeader
     */
    int BALL_SLOGAN = 1;

    /**
     * This view can be simply used without any labels.
     *
     * @see org.ptrheader.library.netease.NetEaseMarsView
     */
    int NET_EASE_MARS_VIEW = 2;

    /**
     * @see org.ptrheader.library.netease.NetEaseNewsHeader
     */
    int NET_EASE_NEWS_HEADER = 3;

    /**
     * @see org.ptrheader.library.ingkee.IngkeeHeader
     */
    int INGKEE_HEADER = 4;
}
