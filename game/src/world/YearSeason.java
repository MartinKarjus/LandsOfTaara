package world;

/**
 * Created by Martin Karjus 1 on 04/03/2017.
 */
public class YearSeason {
    /**
     * W: winter
     * S: summer
     * A: autumn
     * P: spring
     */
    private String season;
    private int year;

    public boolean isOlder(YearSeason olderPerson) {
        if(olderPerson.getYear() < this.year) {
            return true;
        } else if (olderPerson.getYear() > this.year) {
            return false;
        } else if(isEarlier(olderPerson.getSeason())) {
            return false;
        }
        return true;
    }

    public String getSeason() {
        return season;
    }

    public String getSeasonFullName() {
        return getSeasonName(season);
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * True if compared to is earlier.
     * @param comparedTo
     * @return
     */
    public boolean isEarlier(String comparedTo) {
        if(this.season.equals(comparedTo)) {
            return true;
        }
        if(this.season.equals("A") & (comparedTo.equals("S") || comparedTo.equals("P"))) {
            return true;
        }
        if(this.season.equals("W") & (comparedTo.equals("S") || comparedTo.equals("A") || comparedTo.equals("S"))) {
            return true;
        }
        if(this.season.equals("S") & comparedTo.equals("P")) {
            return true;
        }
        return false;
    }

    public YearSeason(String season, int year) {
        this.season = season;
        this.year = year;
    }

    public String getSeasonName(String season) {
        switch (season) {
            case "W":
                return "Winter";
            case "S":
                return "Summer";
            case "A":
                return "Autumn";
            case "P":
                return "Spring";
        }
        return "";
    }

    public void nextSeason() {
        switch (season) {
            case "W":
                year += 1;
                season = "P";
                break;
            case "S":
                season = "A";
                break;
            case "A":
                season = "W";
                break;
            case "P":
                season = "S";
                break;
        }

    }
}
