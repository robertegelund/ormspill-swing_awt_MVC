
public class Controller {
    private View view;
    private Model model;
    int antRader, antKolonner;
    Thread slangeTraad = null;

    Controller(int antRader, int antKolonner, int antSkatter) {
        this.antRader = antRader; this.antKolonner = antKolonner;
        view = new View(this, antRader, antKolonner);
        model = new Model(view, antRader, antKolonner, antSkatter);

        class Slange implements Runnable {
            @Override
            public void run() {
                try {
                    while(!model.hentSpillErSlutt()) {
                        Thread.sleep(1000);
                        flyttSlange(hentSlangeRetning());
                    }
                    view.gameOver();
                } catch (InterruptedException e) {
                    System.out.println("Slangetraaden ble avbrutt.");
                } catch (ArrayIndexOutOfBoundsException e) {
                    model.endreSpilletErSlutt();
                    view.gameOver();
                }
            }
        }
        slangeTraad = new Thread(new Slange());
        slangeTraad.start();
    }
    
    public void flyttSlange(String retning) {
       model.flyttSlange(retning);
    }

    public void leggTilSlangedel(String retning, int rad, int kolonne) {
        model.leggTilSlangedel(retning, rad, kolonne);
    } 


    public void endreRetning(String retning) {
       model.endreRetning(retning);
    }

    public String hentSlangeRetning() {
        return model.hentSlangeRetning();
    }
}