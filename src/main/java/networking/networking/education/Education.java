package networking.networking.education;

import jakarta.persistence.*;
import lombok.*;
import networking.networking.user.User;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "educations")
@Builder
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String schoolName;
    private LocalDate startDate;
    private LocalDate endDate;
}
