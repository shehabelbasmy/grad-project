package functional.programming;

public class Movie //implements Comparable<Movie>
{
    private double rating;

    private String name;

    private int year;

//    @Override //return negative=less than |
//              // zero=equal |
//              // positive= greater than
//    public int compareTo(Movie o) {
//        return o.getYear()-this.year;
//    }

    public Movie(String name,double rating, int year) {
        this.rating = rating;
        this.name = name;
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "rating=" + rating +
                ", name='" + name + '\'' +
                ", year=" + year +
                '}';
    }


}
