package shop.myshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyShop {
    public static void main(String[] args) {
        Kund kund = new Kund();
        
        Vara vara1 = new Vara("Uppstoppat husdjur", 369.50, 10);
        Vara vara2 = new RabatteradVara("Inlagda musungar", 149.50, 32, 50);
        Vara vara3 = new Vara("Rostade kackorlackor", 49.50, 40);

        List<Vara> varor = new ArrayList<>();
        varor.add(vara1);
        varor.add(vara2);
        varor.add(vara3);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Meny:");
            for (int i = 0; i < varor.size(); i++) {
                Vara vara = varor.get(i);
                System.out.println((i + 1) + ". " + vara.getNamn() + ", " + vara.getPris() + " SEK, " + vara.getAntalKvar() + " st kvar i lager");
            }
            System.out.println((varor.size() + 1) + ". Avsluta\n");


            System.out.print("Välj ett alternativ: ");
            int val = scanner.nextInt();

            if (val == varor.size() + 1) {
                break;
            }

            if (val > 0 && val <= varor.size()) {
                Vara valdVara = varor.get(val - 1);
                if (valdVara.getAntalKvar() > 0) {
                    kund.kopVara(valdVara);
                    valdVara.uppdateraAntalKopta();
                    System.out.println("Ditt saldo efter köpet: " + kund.getTotalaSumma() + " SEK\n");
                } else {
                    System.out.println("Den här produkten är slutsåld. Välj något annat.\n");
                }
            } else {
                System.out.println("Ogiltigt val. Försök igen.\n");
            }
        }

        System.out.println("Du har köpt totalt " + kund.getAntalVarorKopta() + " varor. Den totala summan blir " + kund.getTotalaSumma() + " SEK");

    }
}
class Kund {
    private int antalVarorKopta;
    private double totalaSumma;

    public Kund() {
        antalVarorKopta = 0;
        totalaSumma = 0.0;
    }

    public int getAntalVarorKopta() {
        return antalVarorKopta;
    }

    public double getTotalaSumma() {
        return totalaSumma;
    }

    public void kopVara(Vara vara) {
        antalVarorKopta++;
        totalaSumma += vara.getPris();
        vara.minskaAntalKvar();
    }
}

class Vara {
    private String namn;
    private double pris;
    private int antalKopta;
    private int antalKvar;

    public Vara(String namn, double pris, int antalKvar) {
        this.namn = namn;
        this.pris = pris;
        this.antalKopta = 0;
        this.antalKvar = antalKvar;
    }

    public String getNamn() {
        return namn;
    }

    public double getPris() {
        return pris;
    }

    public int getAntalKvar() {
        return antalKvar;
    }
     public void uppdateraAntalKopta() {
        antalKopta++;
    }

    public void minskaAntalKvar() {
        if (antalKvar > 0) {
            antalKvar--;
        }
    }
}

class RabatteradVara extends Vara {
    private double rabatt;

    public RabatteradVara(String namn, double pris, int antalKvar, double rabatt) {
        super(namn, pris, antalKvar);
        this.rabatt = rabatt;
    }

    public double getPris() {
        return super.getPris() * (1 - rabatt / 100);
    }
}
