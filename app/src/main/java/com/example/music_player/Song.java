package com.example.music_player;

//enum SongType{
//    NewMusic,
//    Top,
//    ArabicSong,
//    EnglishSongs
//}
/**
 * {@link Song} represents songs that user want to listen.
 * It contains a name of song, an artist name, and an image  for that song.
 */
public class Song {

    // Artist name for the song
    private String mArtistName;

    // The song name
    private String mNameOfSong;

    // Audio resource ID for the song
    private int mAudioResourceId;
    private boolean isFavourite;
//    private SongType songType;

    // Image resource ID for the song
    private int mImageResourceId = NO_IMAGE_PROVIDED ;

    // Constant value that represents no image was provided for this word
    private static final int NO_IMAGE_PROVIDED = -1;


    /**
     * Create a new Song object.
     *
     * @param artistsName is the artist name for the spisific song
     * @param nameOfSong is the name of the song that the user wants to listen
     * @param imageResourceId is the drawable resource ID for the image associated with the song
     * @param audioResourceId is the resource ID for the audio file associated with this song
     */
    public Song(String artistsName, String nameOfSong, int imageResourceId, int audioResourceId) {
        mArtistName = artistsName;
        mNameOfSong = nameOfSong;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
        this.isFavourite = false;
    }

//    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId, SongType songType) {
//        mDefaultTranslation = defaultTranslation;
//        mMiwokTranslation = miwokTranslation;
//        mImageResourceId = imageResourceId;
//        mAudioResourceId = audioResourceId;
//        this.songType = songType;
//        this.isFavourite = false;
//    }

    // Get the Artists name of the Song.
    public String getArtistName() {

        return mArtistName;
    }

    // Get the name of the song.
    public String getNameOfSong() {

        return mNameOfSong;
    }

    //  Return the image resource ID of the song.
    public int getImageResourceId() {
        return mImageResourceId;
    }

    // Returns whether or not there is an image for this song.
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    // Return the audio resource ID of the song.
    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

//    public SongType getSongType() {
//        return songType;
//    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}

//class FakeSongs{
//    public static List<Word> songsList = new ArrayList<Word>(){
//        {
//            new Word("Ramy Ayach", "Qesset Hob", R.drawable.english_qesset_hob, R.raw.qesset_hob, SongType.ArabicSong);
//            new Word("Ramy Ayach", "Qesset Hob", R.drawable.english_qesset_hob, R.raw.qesset_hob, SongType.ArabicSong);
//        }
//
//    };
//}