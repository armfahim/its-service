package com.its.service.entity.paperwork;

import com.its.service.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "PAPERWORKS",uniqueConstraints = {
        @UniqueConstraint(name = "UniqueMonthAndYear", columnNames = {"month", "year"})})
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paperworks extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PAPERWORK_TITLE", nullable = false, unique = true, length = 25)
    private String paperworkTitle;

    @Column(name = "MONTH", nullable = false)
    private String month;

    @Column(name = "YEAR", nullable = false,length = 4)
    private String year;

    @Column(name = "IS_COMPLETED")
    private Boolean isCompleted = false;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paperworks", cascade = CascadeType.ALL)
    private List<PaperworkBreakdown> paperworkBreakdownList;
}
