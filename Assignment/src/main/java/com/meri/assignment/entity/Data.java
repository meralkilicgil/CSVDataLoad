package com.meri.assignment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="data")
@Getter
@Setter
public class Data {

    @Id
    @Column(name = "code")
    private String code;

    @Column(name="source")
    private String source;

    @Column(name = "codeListCode")
    private String codeListCode;

    @Column(name = "displayValue")
    private String displayValue;

    @Column(name = "longDescription")
    private String longDescription;

    @Column(name = "fromDate")
    private String fromDate;

    @Column(name = "toDate")
    private String toDate;

    @Column(name = "sortingPriority")
    private String sortingPriority;

}
