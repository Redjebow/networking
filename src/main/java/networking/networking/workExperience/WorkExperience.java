package networking.networking.workExperience;

import jakarta.persistence.*;
import lombok.*;
import networking.networking.user.User;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_experience")
@Builder
public class WorkExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String companyName;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
}
