package com.feeham.obla.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "borrows")
public class Borrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrow_id")
    private Long borrowId;

    @Column(name = "due_date", nullable = false, updatable = false)
    private Date dueDate;

    @Column(name = "return_date")
    private Date returnDate;


    /*
    THE DELETE ANOMALY -->
    - We can not delete a book until all of its borrows are deleted. (Foreign key constraint)
    - So while deleting a book we need to delete the borrows too.
        Consequences: But if the corresponding borrows are deleted then if any user who borrowed
        the book tries to see their borrow history will not be able to see the borrows
        with the deleted books.
        (Information integrity violation!)
    - If we do not want to delete the borrows then we can not keep direct relationship
        Consequences: We can not retrieve/fetch related data in straightforward manner as well.
        While retrieving related data, as the book/user is deleted, the users' name, books title
        will be null.
        (Information integrity violation!)
    - So we can keep two additional fields for each parental reference (i.e.: userId, userName)
        Consequences: Data duplication
        (Database normalization violation!)

    The only remaining way is SOFT DELETION MECHANISM.
        - Consequences: The delete api will never truly do its appropriate work.
        (No major issues)


        @Column(name = "user_id", nullable = false)
        private Long userId;

        @Column(name = "user_full_name", nullable = false)
        private String userFullName;
    */

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}