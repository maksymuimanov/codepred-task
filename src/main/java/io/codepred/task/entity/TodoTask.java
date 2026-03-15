package io.codepred.task.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "todo_tasks")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TodoTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;
    @Column(nullable = false)
    private String title;
    @Nullable
    private String description;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TodoStatus status;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Override
    public final boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        Class<?> oEffectiveClass = object instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : object.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        TodoTask todoTask = (TodoTask) object;
        return this.getId() != null && Objects.equals(this.getId(), todoTask.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy hibernateProxy ? hibernateProxy.getHibernateLazyInitializer().getPersistentClass().hashCode() : this.getClass().hashCode();
    }
}
