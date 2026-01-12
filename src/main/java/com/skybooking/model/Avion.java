import loombook.*;

@Data
@AllArgsContructor
@NoArgsContructor
@Entity(name="aviones")
public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String matricula;
    @Column
    private String modelo;
    @Column(name = "capacidad_turista")
    private String capacidadTurista;
    @Column(name = "capacidad_business")
    private String capacidadBusiness;
}