package client;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockThread extends Thread {
    private JLabel clockLabel;
    private boolean running = true;

    public ClockThread(JLabel clockLabel) {
        this.clockLabel = clockLabel;
    }

    @Override
    public void run() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        while (running) {
            try {
                String time = sdf.format(new Date());
                SwingUtilities.invokeLater(() -> clockLabel.setText(time));
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopClock() {
        running = false;
    }
}
