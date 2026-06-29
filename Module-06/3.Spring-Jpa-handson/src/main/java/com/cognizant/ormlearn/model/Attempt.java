package com.cognizant.ormlearn.model;

import java.time.LocalDateTime;
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
 * Persistence class for the ATTEMPT table (quiz application - Hands On 3).
 * Represents a single quiz attempt made by a user at a particular date/time.
 *
 * NOTE on fetch type: attemptQuestionList is mapped LAZY by default here;
 * the Hands On 3 HQL solution uses explicit 'join fetch' at the query level
 * (see AttemptRepository) rather than relying on entity-level EAGER fetch,
 * following the same lesson learned in Hands On 2 about controlling fetch
 * behavior at the query rather than the mapping.
 */
@Entity
@Table(name = "attempt")
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "at_id")
    private Integer id;

    @Column(name = "at_date")
    private LocalDateTime attemptedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "at_us_id")
    private User user;

    @OneToMany(mappedBy = "attempt", fetch = FetchType.LAZY)
    private List<AttemptQuestion> attemptQuestionList;

    public Attempt() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getAttemptedDate() {
        return attemptedDate;
    }

    public void setAttemptedDate(LocalDateTime attemptedDate) {
        this.attemptedDate = attemptedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AttemptQuestion> getAttemptQuestionList() {
        return attemptQuestionList;
    }

    public void setAttemptQuestionList(List<AttemptQuestion> attemptQuestionList) {
        this.attemptQuestionList = attemptQuestionList;
    }

    @Override
    public String toString() {
        return "Attempt [id=" + id + ", attemptedDate=" + attemptedDate + "]";
    }
}
