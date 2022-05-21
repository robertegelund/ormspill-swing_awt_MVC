import java.util.ArrayList;
import java.util.List;

public class Model {
    int antRader, antKolonner, antSkatter;
    private String[][] skatter;
    private List<int[]> slange;
    List<int[]> slangeKopi = null;
    private View view;
    String forrigeRetning = null;
    String slangeRetning = "OST";
    boolean spillErSlutt = false;

    Model(View view, int antRader, int antKolonner, int antSkatter) {
        this.view = view;
        this.antRader = antRader; this.antKolonner = antKolonner;
        skatter = new String[antRader][antKolonner];
        slange = new ArrayList<>();
        
        leggTilSlangehode();
        for(int i = 0; i < antSkatter; i++) leggTilSkatt(); 
    }

    public List<int[]> hentSlange() { return slange; }
    public String hentSlangeRetning() { return slangeRetning; }
    public int hentSlangeLengde() { return slange.size(); }
    public boolean hentErSpillSlutt() { return spillErSlutt; }
    public void endreSpilletErSlutt() { spillErSlutt = true;}

    public void flyttSlange(String retning) {
        skjulSlangedeler();
        
        int rad = slange.get(slange.size() - 1)[0]; 
        int kolonne = slange.get(slange.size() - 1)[1];
        
        if(retning.equals("NORD")) rad--;
        else if(retning.equals("SOR")) rad++;
        else if(retning.equals("VEST")) kolonne--;
        else if(retning.equals("OST")) kolonne++;
        
        if(skatter[rad][kolonne] == null) { 
            slange.remove(0);
            slange.add(new int[] {rad, kolonne} );
        } else if (skatter[rad][kolonne] != null) {
            fjernSkatt(rad, kolonne);
            leggTilSlangedel(rad, kolonne);
            leggTilSkatt();
        }

        if(!trefferSlangenSegSelv()) { 
            visSlangedeler();
            view.visSlangeLengde(slange.size());
        } else {
            spillErSlutt = true;
        }
    }

    public void endreRetning(String retning) {
        forrigeRetning = slangeRetning;
        if(retning.equals("VEST") && !forrigeRetning.equals("OST")) slangeRetning = "VEST";
        else if(retning.equals("NORD") && !forrigeRetning.equals("SOR")) slangeRetning = "NORD";
        else if(retning.equals("OST") && !forrigeRetning.equals("VEST")) slangeRetning = "OST";
        else if(retning.equals("SOR") && !forrigeRetning.equals("NORD")) slangeRetning = "SOR";
    }

    public void leggTilSkatt() {
        int rad = (int)(Math.random() * antRader);
        int kolonne = (int)(Math.random() * antKolonner);

        for(int[] posisjon : slange) {
            int indeks = slange.indexOf(posisjon);
            int sRad = slange.get(indeks)[0]; int sKol = slange.get(indeks)[1];
            if(sRad == rad && sKol == kolonne) {
                rad = (int)(Math.random() * antRader);
                kolonne = (int)(Math.random() * antKolonner);
            }
        }

        if(skatter[rad][kolonne] != null) { 
            leggTilSkatt(); 
        } else if(skatter[rad][kolonne] == null) { 
            skatter[rad][kolonne] = "$"; 
            view.tegnSkatt(rad, kolonne); 
        }
    }

    public void leggTilSlangehode() {
        int rad = (antRader / 2) - 1;
        int kolonne = (antKolonner / 2) - 1;
        int[] posisjon = new int[] {rad, kolonne};
        view.tegnSlangedel("O", rad, kolonne);
        slange.add(posisjon);
        view.visSlangeLengde(hentSlangeLengde());
    }

    public void leggTilSlangedel(int rad, int kolonne) {
        slange.add(new int[] {rad, kolonne});
    }

    public void visSlangedeler() {
        for(int[] posisjon : slange) {
            int indeks = slange.indexOf(posisjon);
            int r = slange.get(indeks)[0];
            int k = slange.get(indeks)[1];
            if(indeks == slange.size() - 1) view.tegnSlangedel("O", r, k);
            else if(indeks != slange.size() - 1) view.tegnSlangedel("+", r, k);
        }
    }

    public void skjulSlangedeler() {
        for(int[] posisjon : slange) {
            int indeks = slange.indexOf(posisjon);
            int r = slange.get(indeks)[0];
            int k = slange.get(indeks)[1];
            view.fjernSlangedel(r, k);
        }
    } 

    public void fjernSkatt(int rad, int kolonne) {
        skatter[rad][kolonne] = null;
    }

    public boolean trefferSlangenSegSelv() {
        for(int[] p1 : slange) {
            int indeks1 = slange.indexOf(p1);
            int r1 = slange.get(indeks1)[0]; int k1 = slange.get(indeks1)[1];
            for(int[] p2 : slange) {
                int indeks2 = slange.indexOf(p2);
                int r2 = slange.get(indeks2)[0]; int k2 = slange.get(indeks2)[1];
                if(indeks1 != indeks2 && r1 == r2 && k1 == k2) {
                    return true;
                }
            }
        }
        return false;
    }
}