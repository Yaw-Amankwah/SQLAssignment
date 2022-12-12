package com.example.sqlassignment;

import javafx.scene.canvas.GraphicsContext;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

public class HistogramAlphaBet {
    private Map<Character,Integer> frequency = new HashMap<>();
    private Map<Character,Double> probability = new HashMap<>();

    HistogramAlphaBet(){};
    HistogramAlphaBet (Map<Character, Integer> f) {
        setFrequency(sortFMapByKey(f));
        setProbability(createProbabilityMap());
    }

    HistogramAlphaBet(HistogramAlphaBet h) {// COPY CONSTRUCTOR. JUST COPIES THE MAPS FROM THE ARGUMENT.
        setFrequency(h.getFrequency());
        setProbability(h.getProbability());
    }
    HistogramAlphaBet (String str) { //CREATE OBJECT FROM GIVEN STRING
        setFrequency(createFrequencyMap(str));
        setProbability(createProbabilityMap());
    }

    //GETTERS
    public Map<Character, Integer> getFrequency() {
        return frequency;
    }
    public Map<Character, Double> getProbability() {
        return probability;
    }

    //SETTERS
    public void setFrequency(Map<Character, Integer> f) {
        frequency = sortFMapByKey(f);
    }
    public void setProbability(Map<Character, Double> p) {
        probability = sortPMapByKey(p);
    }
    public Map<Character, Double> createProbabilityMap () { //GENERATE PROBABILITY MAP FROM FREQUENCY MAP
        int sum = this.getTotalFrequency();
        Map<Character, Double> p = new HashMap<>();
        for (Character c: this.getFrequency().keySet()) {
            p.put(c, Double.valueOf(this.getFrequency().get(c))/sum);
        }
        return p;
    }
    public Map<Character, Integer> createFrequencyMap(String str) {
        String r = str.replaceAll("[^a-zA-Z]", "").toLowerCase(); // remove all non-alphabet characters
        Map<Character, Integer> my_map = new HashMap<>();

        for (int i = 0; i < r.length(); i++) { // fill up frequency hashmap
            char c = r.charAt(i);
            if (my_map.containsKey(c)) {
                my_map.put(c, my_map.get(c) + 1);
            }
            else {
                my_map.put(c, 1);
            }
        }
        return my_map;
    }

    //METHODS
    public int getTotalFrequency() { //RETURN SUM OF ALL FREQUENCIES
        return this.getFrequency().values().stream().mapToInt(Integer::intValue).sum();

    }
    public double getTotalProbability() { // RETURN SUM OF ALL PROBABILITIES. SHOULD EQUAL 1.
        return this.getProbability().values().stream().mapToDouble(Double::doubleValue).sum();
    }
    public Map<Character, Integer> sortFMapByValue (Map<Character, Integer> my_map) {
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();
                my_map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEachOrdered(x -> sortedMap.put(x.getKey(),x.getValue()));
        return sortedMap;
    }
    public Map<Character, Integer> sortFMapByKey (Map<Character, Integer> my_map) {
        Map<Character, Integer> sortedMap = new LinkedHashMap<>();
        my_map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(),x.getValue()));
        return sortedMap;
    }
    public Map<Character, Double> sortPMapByValue (Map<Character, Double> my_map) {
        Map<Character, Double> sortedMap = new LinkedHashMap<>();
                my_map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }
    public Map<Character, Double> sortPMapByKey (Map<Character, Double> my_map) {
        Map<Character, Double> sortedMap = new LinkedHashMap<>();
        my_map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        return sortedMap;
    }
    public static HistogramAlphaBet openReadFile(String fileName) {

        String result = new String();
        Scanner input = null;

        try {
            input = new Scanner(Paths.get(fileName));
        }
        catch (IOException ioException) {
            System.err.println("File is not found");
        }
        try {
            while (input.hasNext()) {
                result+=input.nextLine().replaceAll("[^a-zA-Z]","").toLowerCase();
            }
            System.out.println(result.length());
        } catch (NoSuchElementException elementException) {
            System.err.println("Invalid input! Terminating...");
        }
        if (input != null) {input.close();}
        return new HistogramAlphaBet(result);
    }

    //NON-STATIC INNER CLASS
    public class MyPieChart {
        private Map <Character, Slice> slices = new HashMap<>();
        private int n;
        private MyPoint center;
        private int radius;
        private double rotateAngle;

        MyPieChart (int n, MyPoint center, int r, double rotateAngle) {
            this.n = n;
            this.center = center;
            this.radius = r;
            this.rotateAngle = Optional.ofNullable((rotateAngle)).orElse(0.0);

            probability = getProbability();
            if (n > probability.size()) {
                throw new IllegalArgumentException("n has to be smaller than " + probability.size());
            }
            slices = getMyPieChart();
        }

//        public MyPoint getCenter() {
//            return center;
//        }
//        public int getRadius() {
//            return radius;
//        }
//
//        public double getRotateAngle() {
//            return rotateAngle;
//        }

        public Map<Character, Slice> getMyPieChart() {
            //CREATE COLOR ARRAYLIST WITHOUT GRAY
            ArrayList<MyColor> colors = new ArrayList<>();
            for (MyColor color: MyColor.values()) {
                if ((color != MyColor.WHITE ) || (color != MyColor.GRAY)) {
                    colors.add(color);
                }
            }
            //RANDOMIZE THE COLORS
            Collections.shuffle(colors);
            int i = 0;

            double startAngle = rotateAngle;
            Map<Character, Slice> sorted = new LinkedHashMap<>();
            for (Character Key : probability.keySet()) {
                double angle = 360.0 * probability.get(Key);
                sorted.put(Key, new Slice(center, radius, startAngle, angle, colors.get(i)));
                startAngle += angle;
                i++;
            }
            return sorted;
        }

        public Map<Character, Slice> getSlices() {
            return slices;
        }

        public void printOut() {
            System.out.println("char      prob");
            System.out.println("----      ----");
            int i = 0;
            double sum_probabilities = 0;
            DecimalFormat df = new DecimalFormat("#.##");
            for (Character Key: slices.keySet()) {
                if (i < n) {
                    System.out.println(Key + ":        " + df.format (probability.get(Key)));
                    sum_probabilities += probability.get(Key);
                    i++;
                }
            }
//            if (n < 26) {
//                System.out.println("others:   " + df.format(1 - sum_probabilities));
//            }
        }
        public void draw (GraphicsContext gc) {
            int i = 0;
            double sum_angles = 0;
            double end_angle = 0;
            for (Character Key: slices.keySet()) {
                if (i < n) {
                    slices.get(Key).draw(gc);
                    sum_angles += slices.get(Key).getExtent();
                    end_angle = slices.get(Key).getEndAngle();
                    i++;
                }
            }
//            if (n < 26) {
//                Slice rest = new Slice(center, radius, end_angle, 360 - sum_angles, MyColor.GRAY);
//                rest.draw(gc);
//            }
        }
    }
}
