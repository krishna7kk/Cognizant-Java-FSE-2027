package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Persistence class for the OPTIONS table (quiz application - Hands On 3).
 * Holds master data for each answer option belonging to a question,
 * including its score (used to determine the correct answer - the option(s)
 * with score > 0 are correct).
 */
@Entity
@Table(name = "options")
public class Options {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "op_id")
    private Integer id;

    @Column(name = "op_text")
    private String text;

    @Column(name = "op_score")
    private double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "op_qu_id")
    private Question question;

    public Options() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Options [id=" + id + ", text=" + text + ", score=" + score + "]";
    }
}
