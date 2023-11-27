Czego nie rozumiem:

1) Jak mapowac recordy w hibernacie?
Mamy klasy InvoiceId, CustomerId i ProductId, ktore maja nam przetrzymywac
klucze do naszych klas ale nie wiem jak je mapowac. Widzialem rozwiazanie
z adnotacjami @Embedded i @Embeddeable ale nie wiem czy to jest dobre rozwiazanie
i jak to sie zachowuje w praktyce. DLATEGO ZAMIENILEM typy w klasach CustomerEntityDatabase
z CustomerId na stricte UUID.

tak to zrobilem:

public class CustomerDatabaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id")
    private UUID customerId;
}


a tak to mam napisane w domenie:

public class Customer {
@EqualsAndHashCode.Include
    private CustomerId customerId;
}

public record CustomerId(String value) {

}




2) Jak zmapowac klase Enterpreneurship, Nip w hibernacie.
Chce aby powstala jedna tabela - customer, gdzie beda
widnialy wszystkie dane dotyczacego tego customera.
Dlatego widzialem rozwiazanie tego problemu w ten sposob:

   

@Enumerated(EnumType.STRING)
@Column(name = "entrepreneurship_form")
private EntrepreneurshipForm entrepreneurshipForm;
@Embedded
private TaxPaymentForm taxPaymentForm;


a pozniej w klasie taxPaymentForm;

@MappedSuperclass
public abstract class TaxPaymentFormDatabase {
@Enumerated(EnumType.STRING)
@Column(name = "tax_rate")
protected TaxRate taxRate;
}

z ktorej beda dziedziczyc LumpSumTaxDatabase, GeneralTaxDatabase i FlatTaxDatabase




3) Przypadek z Invoice chyba wyjatkowo rozumiem -> klasa abstrakcyjna
InvoiceDatabase, po ktorej dziedzicza costInvoiceDatabaseEntity
i incomeInvoiceDatabaseEntity i robimy tabele tylko na podstawie
tych 2 typow invoicea za pomoca adnotacji @MappedSuperClass, 
Jedynym problemem tutaj jest znowu problem z mapowaniem recordu InvoiceId
w hibernacie oraz recordy Money/Price, ktore znajduja sie w tych 
typach invoicow
czyli:


public class IncomeInvoice extends Invoice{
private final InvoiceType invoiceType = InvoiceType.INCOME_INVOICE;
    private Money amount;
}
 

oraz to samo dla CostInvoicow


4) Architektura jak ma wygladac - Tutaj chcialem stworzyc wszystkie klasy,
ktore jakkolwiek komunikuja sie z baza danych - aby nie dodawac adnotacji
w klasach domenowych, ale nie wiem czy to jest dobra metoda. Dlatego
powstaly klasy takie jak InvoiceDatabase,
CostInvoiceDatabaseEntity(tutaj dodaje sufix Entity, kiedy wiem, ze
klasa ta bedzie stanowila encje w bazie danych)











