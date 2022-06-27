package by.dvn.scooterrental.model.rental;

import by.dvn.scooterrental.model.IModelObject;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "rental_points")
public class RentalPoint implements IModelObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "FK_PARENT_ID"))
    private RentalPoint parent;

    public RentalPoint() {
    }

    public RentalPoint(Integer id, String name, RentalPoint parent) {
        this.id = id;
        this.name = name;
        this.parent = parent;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName(String fullName, RentalPoint rentalPoint) {
        if (rentalPoint != null) {
            fullName += rentalPoint.getName() + " ";
            rentalPoint = rentalPoint.getParent();
            fullName = getFullName(fullName, rentalPoint);
        }
        return fullName;
    }

    public RentalPoint getParent() {
        return parent;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(RentalPoint parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentalPoint)) return false;
        RentalPoint that = (RentalPoint) o;
        return getId().equals(that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getParent(), that.getParent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getParent());
    }

}
