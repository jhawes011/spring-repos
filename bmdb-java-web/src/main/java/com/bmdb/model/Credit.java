package com.bmdb.model;

import jakarta.persistence.*;
@Entity
public class Credit {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@ManyToOne
@JoinColumn(name = "actorID")
private Actor actor;
@ManyToOne
@JoinColumn(name = "movieID")
private Movie movie;
private String role;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Actor getActor() {
	return actor;
}
public void setActor(Actor actor) {
	this.actor = actor;
}
public Movie getMovie() {
	return movie;
}
public void setMovie(Movie movie) {
	this.movie = movie;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}

}
