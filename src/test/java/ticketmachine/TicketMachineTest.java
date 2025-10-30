package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50;

	private TicketMachine machine;

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE);
	}

	@Test
	void priceIsCorrectlyInitialized() {
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	void noPrintIfAmountNotEnough(){
		machine.insertMoney(PRICE - 1);
		boolean result = machine.printTicket();
		assertFalse(result, "Le ticket ne devrait pas être imprimé si le montant est insuffisant");
	}

	@Test
	void printTicketIfEnoughMoney() {
		machine.insertMoney(PRICE);
		boolean result = machine.printTicket();
		assertTrue(result, "Le ticket devrait être imprimé si le montant est suffisant");
	}

	@Test
	void balanceDecreasesWhenTicketPrinted() {
		machine.insertMoney(PRICE + 20);
		machine.printTicket();
		assertEquals(20, machine.getBalance(), "La balance devrait être réduite du prix du ticket");
	}

	@Test
	void totalUpdatedOnlyWhenTicketPrinted() {
		assertEquals(0, machine.getTotal(), "Le total devrait être à 0 initialement");
		machine.insertMoney(PRICE);
		assertEquals(0, machine.getTotal(), "Le total ne devrait pas changer avant l'impression");
		machine.printTicket();
		assertEquals(PRICE, machine.getTotal(), "Le total devrait être mis à jour après l'impression");
	}

	@Test
	void refundReturnsCorrectAmount() {
		machine.insertMoney(30);
		int refunded = machine.refund();
		assertEquals(30, refunded, "Le montant remboursé devrait être égal à la balance");
	}

	@Test
	void refundResetsBalance() {
		machine.insertMoney(50);
		machine.refund();
		assertEquals(0, machine.getBalance(), "La balance devrait être remise à zéro après remboursement");
	}

	@Test
	void cannotInsertNegativeAmount() {
		assertThrows(IllegalArgumentException.class, 
			() -> machine.insertMoney(-10),
			"Insérer un montant négatif devrait lever une exception");
	}

	@Test
	void cannotCreateMachineWithNegativePrice() {
		assertThrows(IllegalArgumentException.class,
			() -> new TicketMachine(-50),
			"Créer une machine avec un prix négatif devrait lever une exception");
	}

	@Test
	void cannotCreateMachineWithZeroPrice() {
		assertThrows(IllegalArgumentException.class,
			() -> new TicketMachine(0),
			"Créer une machine avec un prix à zéro devrait lever une exception");
	}

}
