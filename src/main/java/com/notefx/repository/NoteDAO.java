package com.notefx.repository;

import com.notefx.database.DatabaseConnection;
import com.notefx.models.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteDAO implements NoteRepository {

	private static final String CREATE_TABLE_SQL = """
			CREATE TABLE IF NOT EXISTS notes (
			    id INTEGER PRIMARY KEY AUTOINCREMENT,
			    titulo TEXT NOT NULL,
			    contenido TEXT,
			    fecha_creacion TEXT NOT NULL,
			    ultima_modificacion TEXT NOT NULL
			)
			""";

	private static final String INSERT_SQL = "INSERT INTO notes (titulo, contenido, fecha_creacion, ultima_modificacion) VALUES (?, ?, ?, ?)";

	private static final String FIND_ALL_NOTES = "SELECT id, titulo, contenido FROM notes ORDER BY id DESC";
	
	private static final String FIND_BY_ID_SQL = "SELECT id, titulo, contenido, fecha_creacion, ultima_modificacion FROM notes WHERE id = ?";

	private static final String DELETE_SQL = "DELETE FROM notes WHERE id = ?";
	
	private static final String UPDATE_CONTENIDO_SQL = "UPDATE notes SET contenido = ?, ultima_modificacion = ? WHERE id = ?";

	// Inicializa el DAO y garantiza que la tabla exista en la base de datos.
	public NoteDAO() {
		ensureSchema();
	}

	// ===== CREAR NOTA ======
	// ====================
	
	// Crea una nota nueva con titulo, contenido vacio y fechas de creacion/modificacion actuales.
	@Override
	public Note crearNote(String titulo) {
		LocalDateTime now = LocalDateTime.now();

		try (Connection conn = DatabaseConnection.connect()) {
			if (conn == null) {
				throw new IllegalStateException("No se pudo abrir la conexion SQLite");
			}
			try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
				ps.setString(1, titulo);
				ps.setString(2, "");
				ps.setString(3, now.toString());
				ps.setString(4, now.toString());
				ps.executeUpdate();

				long id = readGeneratedId(ps);
				return new Note(id, titulo, "", now, now);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al crear la nota", e);
		}
	}

	// Crea la tabla notes si todavia no existe.
	private void ensureSchema() {
		try (Connection conn = DatabaseConnection.connect()) {
			if (conn == null) {
				throw new IllegalStateException("No se pudo abrir la conexion SQLite");
			}

			try (Statement st = conn.createStatement()) {
				st.executeUpdate(CREATE_TABLE_SQL);
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al inicializar la tabla notes", e);
		}
	}

	// Obtiene el id autogenerado de la ultima insercion.
	private long readGeneratedId(PreparedStatement ps) throws SQLException {
		try (ResultSet rs = ps.getGeneratedKeys()) {
			if (rs.next()) {
				return rs.getLong(1);
			}
		}
		return -1L;
	}

	// ===== EDITAR NOTA =====
	// ====================

	@Override
	public Note editarNote(Long id, String contenido) {
		if (id == null) {
			throw new IllegalArgumentException("El id de la nota no puede ser null");
		}
		LocalDateTime now = LocalDateTime.now();
		String contenidoSeguro = contenido == null ? "" : contenido;

		try (Connection conn = DatabaseConnection.connect()) {
			if (conn == null) {
				throw new IllegalStateException("No se pudo abrir la conexion SQLite");
			}
			try (PreparedStatement updatePs = conn.prepareStatement(UPDATE_CONTENIDO_SQL)) {
				updatePs.setString(1, contenidoSeguro);
				updatePs.setString(2, now.toString());
				updatePs.setLong(3, id);

				int filasAfectadas = updatePs.executeUpdate();
				if (filasAfectadas == 0) {
					throw new IllegalArgumentException("No existe una nota con id: " + id);
				}
			}

			try (PreparedStatement findPs = conn.prepareStatement(FIND_BY_ID_SQL)) {
				findPs.setLong(1, id);
				try (ResultSet rs = findPs.executeQuery()) {
					if (rs.next()) {
						return new Note(
							rs.getLong("id"),
							rs.getString("titulo"),
							rs.getString("contenido"),
							LocalDateTime.parse(rs.getString("fecha_creacion")),
							LocalDateTime.parse(rs.getString("ultima_modificacion"))
						);
					}
				}
			}

			throw new IllegalStateException("La nota fue editada, pero no se pudo recuperar desde la base de datos");
		} catch (SQLException e) {
			throw new RuntimeException("Error al editar el contenido de la nota", e);
		}
	}

	// ==== ELIMINAR NOTA ====
	// ====================

	@Override
	public boolean eliminarPorId(Long id) {
		if (id == null) {
			return false;
		}
		try (Connection conn = DatabaseConnection.connect()) {
			if (conn == null) {
				throw new IllegalStateException("No se pudo abrir la conexion SQLite");
			}
			try (PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
				ps.setLong(1, id);
				return ps.executeUpdate() > 0;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error al eliminar la nota", e);
		}
	}

	// ==== LISTA DE NOTAS ====
	// ====================

	@Override
	public List<Note> verLista() {
		List<Note> notas = new ArrayList<>();

		try (Connection conn = DatabaseConnection.connect()) {
			if (conn == null) {
				throw new IllegalStateException("No se pudo abrir la conexión SQLite");
			}

			try (PreparedStatement ps = conn.prepareStatement(FIND_ALL_NOTES); ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Note note = new Note(null);
					note.setId(rs.getLong("id"));
					note.setTitulo(rs.getString("titulo"));
					note.setContenido(rs.getString("contenido"));
					notas.add(note);
				}
			}

			return notas;

		} catch (SQLException e) {
			throw new RuntimeException("No se encuentran las notas", e);
		}
	}

	@Override
	public Optional<Note> obtenerPorTitulo(Note note) {
		// TODO Esbozo de método generado automáticamente
		return Optional.empty();
	}

}