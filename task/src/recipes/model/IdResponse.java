package recipes.model;

import java.util.Objects;

public class IdResponse {
    private final Integer id;

    public IdResponse(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdResponse)) return false;
        IdResponse that = (IdResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "IdResponse{" +
                "id=" + id +
                '}';
    }
}
