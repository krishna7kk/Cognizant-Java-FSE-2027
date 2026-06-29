package com.cognizant.ormlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Attempt;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

    /*
     * Hands On 3: Fetch quiz attempt details using HQL.
     *
     * Joins the tables in the order specified by the handout:
     *   user -> attempt -> attempt_question -> question
     *        -> attempt_option -> options
     *
     * 'join fetch' is used wherever the relationship is one-to-many or
     * many-to-many, so the related collections are populated in a single
     * SQL query (same lesson as Hands On 2):
     *   - attempt.attemptQuestionList   (one-to-many)  -> fetch
     *   - attemptQuestion.attemptOptionList (one-to-many) -> fetch
     * Plain 'join' (no fetch) is sufficient for the many-to-one legs
     * (question, option) since those are single related entities, but
     * 'join fetch' is used throughout here so question and option details
     * are eagerly populated as well, avoiding any further lazy-load trips.
     */
    @Query(value = "SELECT a FROM Attempt a "
        + "join a.user u "
        + "left join fetch a.attemptQuestionList aq "
        + "left join fetch aq.question q "
        + "left join fetch aq.attemptOptionList ao "
        + "left join fetch ao.option o "
        + "WHERE u.id = :userId AND a.id = :attemptId")
    Attempt getAttempt(@Param("userId") int userId, @Param("attemptId") int attemptId);
}
