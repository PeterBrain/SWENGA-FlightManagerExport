package at.fh.swenga.jpa.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Flight")

@NamedQueries({
	@NamedQuery(name = "FlightModel.findByNamedQuery", query = "SELECT e FROM FlightModel e WHERE e.origin LIKE :searchString OR e.destination LIKE :searchString")
})

public class FlightModel implements java.io.Serializable {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int flightId;

	@Column(nullable = false, length = 30)
	private String aircraft;

	@Column(nullable = false, length = 30)
	private String origin;

	@Column(nullable = false, length = 30)
	private String destination;

	// Date Only, no time part:
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
	@NotNull(message = "Departure cannot be null")
	private Date departure;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
	@NotNull(message = "Arrival cannot be null")
	private Date arrival;

	private int numberOfPassengers;

	@Column(nullable = false, length = 30)
	private String airline;

	private boolean cancelled;

	@OneToOne(cascade = CascadeType.ALL)
	private DocumentModel document;

	@Version
	long version;

	// constructors
	public FlightModel() {
	} // default constructor

	public FlightModel(int flightId, String aircraft, String origin, String destination, Date departure, Date arrival,
			int numberOfPassengers, String airline, boolean cancelled) {
		super();
		this.flightId = flightId;
		this.aircraft = aircraft;
		this.origin = origin;
		this.destination = destination;
		this.departure = departure;
		this.arrival = arrival;
		this.numberOfPassengers = numberOfPassengers;
		this.airline = airline;
		this.cancelled = cancelled;
	}

	// getter & setter
	public int getFlightId() {
		return flightId;
	}

	public void setFlightId(int flightId) {
		this.flightId = flightId;
	}

	public String getAircraft() {
		return aircraft;
	}

	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public Date getDeparture() {
		return departure;
	}

	public void setDeparture(Date departure) {
		this.departure = departure;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public int getNumberOfPassengers() {
		return numberOfPassengers;
	}

	public void setNumberOfPassengers(int numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public boolean getCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public DocumentModel getDocument() {
		return document;
	}

	public void setDocument(DocumentModel document) {
		this.document = document;
	}

	// equals & hashcode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + flightId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlightModel other = (FlightModel) obj;
		if (flightId != other.flightId)
			return false;
		return true;
	}

	/**
	 * returns a string with all attributes accumulated
	 * 
	 * return
	 */
	@Override
	public String toString() {
		return "FlightModel [flightId=" + flightId + ", aircraft=" + aircraft + ", origin=" + origin + ", destination="
				+ destination + ", departure=" + departure + ", arrival=" + arrival + ", numberOfPassengers="
				+ numberOfPassengers + ", airline=" + airline + ", cancelled=" + cancelled + "]";
	}
}