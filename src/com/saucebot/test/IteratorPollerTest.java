package com.saucebot.test;

import java.util.Scanner;

import com.saucebot.twitch.TimedPriorityQueue;
import com.saucebot.util.IteratorListener;
import com.saucebot.util.IteratorPoller;

public class IteratorPollerTest implements IteratorListener<String> {

    private IteratorPoller<String> poller;

    public IteratorPollerTest() {
        TimedPriorityQueue<String> queue = new TimedPriorityQueue<String>(5, 1000L);
        poller = new IteratorPoller<String>(queue, this, 3000L);

        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String line = scan.nextLine().trim();
            if (line.equals("end")) {
                poller.stop();
                System.out.println("stopped");
                break;
            } else {
                queue.add(line);
            }
        }
        scan.close();
    }

    @Override
    public void onNext(final String string) {
        System.out.println("New element: " + string);
    }

    public static void main(final String[] args) {
        new IteratorPollerTest();
    }

}
