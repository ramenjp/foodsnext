package com.dev_training.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "todo")
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "detail", nullable = false)
    private String detail;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "issue_person_id", nullable = false)
    private int issuePersonId;

    @Column(name = "person_in_charge_id")
    private int personInChargeId;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private String priority;

}