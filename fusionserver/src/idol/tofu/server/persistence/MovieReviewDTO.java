package idol.tofu.server.persistence;


import java.io.Serializable;

public class MovieReviewDTO {
    private int uid;
    private int mark; // 0~5점 영화 평점
    private int movieUid; // 영화고유값
    private String userId; // 사용자 아이디
    private String title;
    private String story;

    public MovieReviewDTO() {}
    public MovieReviewDTO(int uid, int mark, int movieUid, String userId, String title, String story) {
        this.uid = uid;
        this.mark = mark;
        this.movieUid = movieUid;
        this.userId = userId;
        this.title = title;
        this.story = story;
    }

    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

    public int getMark() { return mark; }

    public void setMark(int mark) { this.mark = mark; }

    public int getMovieUid() { return movieUid; }

    public void setMovieUid(int movieUid) { this.movieUid = movieUid; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getStory() { return story; }

    public void setStory(String story) { this.story = story; }

    @Override
    public String toString() {
        return  uid +
                movieUid +  "," +
                userId +  "," +
                title +  "," +
                story +  "," +
                mark
                ;
    }
}
