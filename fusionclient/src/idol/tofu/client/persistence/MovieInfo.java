package idol.tofu.client.persistence;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// Server: MovieDTO.java 기반

public class MovieInfo {
    private int uid;
    private int rating; // 영화 관람등급
    private int price;
    private int runtime;
    private String name;
    private String actor;
    private String genre; // 영화 장르
    private String detail; // 영화 상세정보
    private String trailer; // 영화 트레일러 URL
    private String poster; // 영화 포스터 URL
    private String status; // 영화 상영 상태 F:종영 T:상영 중
    private Calendar releaseDate; // 영화 개봉일

    public MovieInfo() {releaseDate = Calendar.getInstance();}

    public MovieInfo(int uid, int rating, int price, int runtime, String name, String actor, String genre,
                    String detail, String trailer, String poster, String status, Calendar releaseDate) {
        this.uid = uid;
        this.rating = rating;
        this.price = price;
        this.runtime = runtime;
        this.name = name;
        this.actor = actor;
        this.genre = genre;
        this.detail = detail;
        this.trailer = trailer;
        this.poster = poster;
        this.status = status;
        this.releaseDate = releaseDate;
    }


    public int getUid() { return uid; }

    public void setUid(int uid) { this.uid = uid; }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public int getPrice() { return price; }

    public void setPrice(int price) { this.price = price; }

    public int getRuntime() { return runtime; }

    public void setRuntime(int runtime) { this.runtime = runtime; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getActor() { return actor; }

    public void setActor(String actor) { this.actor = actor; }


    public String getGenre() { return genre; }

    public void setGenre(String genre) { this.genre = genre; }

    public String getDetail() { return detail;  }

    public void setDetail(String detail) { this.detail = detail; }

    public String getTrailer() { return trailer; }

    public void setTrailer(String trailer) { this.trailer = trailer; }

    public String getPoster() { return poster; }

    public void setPoster(String poster) { this.poster = poster; }

    public String getStatus() { return status; }

    public void setStatus(String status) {this.status = status;}

    public Calendar getReleaseDate() { return releaseDate; }

    public void setReleaseDate(Calendar releaseDate) { this.releaseDate = releaseDate; }

    public String getReleaseDateString() {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-dd");
        return format.format(releaseDate.getTime());
    }

    public void setReleaseDateFromString(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-dd");
        try {
            releaseDate.setTime(format.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", actor='" + actor + '\'' +
                ", rating='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", status='" + status + '\'' +
                ", price='" + price + '\'' +
                ", runtime='" + runtime + '\'' +
                '}';
    }
}
