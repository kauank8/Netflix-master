package model;

public class Serie {
	public String major_genre;
	public String title;
	public String subgenre;
	public String premiere_year;
	public String episodes;
	public String status;
	public int imdb_rating;
	
	@Override
	public String toString() {
		return   major_genre + ";" + title + ";" + subgenre + ";"
				+ premiere_year + ";" + episodes + "; " + status + "; " + imdb_rating +"\n"
				;
	}	
}
