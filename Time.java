package sample;

import javafx.application.Platform;

public class Time extends Thread{
    int count = 0;
    MainWindow mw;
    volatile boolean shouldRun = true;

    public Time(MainWindow mw){
    this.mw = mw;
    }

    @Override
    public void run() {
        while(shouldRun == true){
            count++;
            Platform.runLater(() ->{
                mw.getTimeText().setText(toTime());
            });
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }


        }

    }

    public void reset(){
        count = 0;
    }

    public String toTime(){
        int temp = count;
        String str = "";

        if(temp >= 600){
            str += Integer.toString(temp / 600);
            temp -= (temp / 600) * 600;
        }else{
            str += "0";
        }

        if(temp >= 60){
            str += Integer.toString(temp / 60) + ":";
            temp -= (temp / 60) * 60;
        }else{
            str += "0:";
        }

        if(temp < 10){
            str += "0" + Integer.toString(temp);
        }else {
            str += Integer.toString(temp);
        }
     return str;
    }

    public void exit(){
        shouldRun = false;
    }
}
