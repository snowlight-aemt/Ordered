package com.practice.order.domain.partner;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;

@Slf4j
@Entity
@NoArgsConstructor
@Table(name = "partners")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String partnerToken;

    private String businessNo;
    private String partnerName;
    private String email;

    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    @Builder
    public Partner(String businessNo, String partnerName, String email) {
        if (businessNo == null) throw new RuntimeException("empty businessNo");
        if (partnerName == null) throw new RuntimeException("empty partnerName");
        if (email == null) throw new RuntimeException("empty email");

        this.partnerToken = "ABCDE";
        this.businessNo = businessNo;
        this.partnerName = partnerName;
        this.email = email;
        this.status = Status.ENABLE;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ENABLE("활성화"), DISABLE("비활성화");
        private final String description;
    }

    public void Enable() {
        this.status = Status.ENABLE;
    }

    public void Disable() {
        this.status = Status.DISABLE;
    }
}

