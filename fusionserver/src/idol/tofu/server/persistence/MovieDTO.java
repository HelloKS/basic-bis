package idol.tofu.server.persistence;

import java.io.Serializable;
import java.sql.Date;

public class MovieDTO {
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
    private Date releaseDate; // 영화 개봉일

    public MovieDTO()
    {
    }
    public MovieDTO(int uid, int rating, int price, int runtime, String name, String actor, String genre,
                    String detail, String trailer, String poster, String status, Date releaseDate) {
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

    public Date getReleaseDate() { return releaseDate; }

    public void setReleaseDate(Date releaseDate) { this.releaseDate = releaseDate; }

    @Override
    public String toString() {
        String detail_replace = "";

        if (detail != null) {
            detail_replace = detail.replace(",", "$comma");
        }

        return uid + "," +
                rating + "," +
                name + ',' +
                actor + ',' +
                genre + ',' +
                releaseDate + ',' +
                status + ',' +
                price + ',' +
                runtime + "," +
                detail_replace;

    }
}
