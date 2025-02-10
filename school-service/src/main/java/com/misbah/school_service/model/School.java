package com.misbah.school_service.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "school")
@Data
public class School {

    @Id
    private String id;
    private String schoolName;
    private String location;
    private String principalName;
}
