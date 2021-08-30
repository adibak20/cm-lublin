package pl.cm.core.model.entities;

import com.google.common.base.MoreObjects;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    private static final String DEFAULT_USER = "SYSTEM";

    @Column(nullable = false, unique = true)
    private final UUID uuid = UUID.randomUUID();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @NotNull
    @Column(name = "tech_ins_ts")
    protected LocalDateTime techInsTs;


    @Column(name = "tech_upd_ts")
    protected LocalDateTime techUpdTs;

    @Version
    @Column(name = "lock_version")
    protected Long lockVersion;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BaseEntity that = (BaseEntity) o;
        return Objects.equals(uuid, that.uuid);
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Long getId() {
        return this.id;
    }

    @PrePersist
    protected void prePersist() {
        this.techInsTs = LocalDateTime.now();
        this.lockVersion = 0L;
    }

    @PreUpdate
    protected void preUpdate() {
        this.techUpdTs = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("uuid", getUuid())
                .toString();
    }
}
