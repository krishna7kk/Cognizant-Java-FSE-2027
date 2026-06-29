package com.cognizant.ormlearn.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Persistence class for the ATTEMPT_QUESTION table (quiz application -
 * Hands On 3). Links a single Attempt to one of the Questions it included,
 * and in turn owns the list of AttemptOption rows recording which option(s)
 * the user selected for this question within this attempt.
 */
@Entity
@Table(name = "attempt_question")
public class AttemptQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aq_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aq_at_id")
    private Attempt attempt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aq_qu_id")
    private Question question;

    @OneToMany(mappedBy = "attemptQuestion", fetch = FetchType.LAZY)
    private List<AttemptOption> attemptOptionList;

    public AttemptQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Attempt getAttempt() {
        return attempt;
    }

    public void setAttempt(Attempt attempt) {
        this.attempt = attempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<AttemptOption> getAttemptOptionList() {
        return attemptOptionList;
    }

    public void setAttemptOptionList(List<AttemptOption> attemptOptionList) {
        this.attemptOptionList = attemptOptionList;
    }

    @Override
    public String toString() {
        return "AttemptQuestion [id=" + id + "]";
    }
}
