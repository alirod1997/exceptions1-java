package model.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import model.exceptions.DomainException;

public class Reservation {
	private Integer roomNumber;
	private Date checkin;
	private Date checkout;

	// instancia um objeto para trabalhar com datas, passando um parêmetro especidia
	// (dia/mês/ano).
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	// construtores
	public Reservation() {
	}

	public Reservation(Integer roomNumber, Date checkin, Date checkout) {
		// programação defensiva: tratamento de exceção dentro do construtor.
		if (!checkout.after(checkin)) {
			throw new IllegalArgumentException("Check-out date must be after check-in date ");
		}
		this.roomNumber = roomNumber;
		this.checkin = checkin;
		this.checkout = checkout;
	}

	// Gets e Sets
	public Integer getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Integer roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Date getCheckin() {
		return checkin;
	}

	public Date getCheckout() {
		return checkout;
	}

	// calcula a duração da hospedagem no hotel em milisegundos.
	public long duration() {
		long diff = checkout.getTime() - checkin.getTime();
		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS); // faz a conversão do valor de milisegundos para
																	// dias.
	}

	// recebe duas datas novas e atualiza o checkin e checkout .
	public void updateDates(Date checkin, Date checkout) throws DomainException {

		// instanciado um objeto do tipo Date com a data atual.
		Date now = new Date();

		// verifica se a data de checkin/checkout é anterior ao dia atual e trata uma
		// exceção de argumento ilegal.
		if (checkin.before(now) || checkout.before(now)) {
			throw new IllegalArgumentException("Reservation dates for update must be future dates");
		}
		// verifica se a data de checkout é depois da data de checkin e trata uma
		// exceção de argumento ilegal.
		if (!checkout.after(checkin)) {
			throw new IllegalArgumentException("Check-out date must be after check-in date ");
		}
		// aponta os parâmetros da classe.
		this.checkin = checkin;
		this.checkout = checkout;
	}

	@Override
	public String toString() {
		return "room" + roomNumber + ", checkin: " + sdf.format(checkin) + ", check-out: " + sdf.format(checkout) + ", "
				+ duration() + " nights";
	}
}
