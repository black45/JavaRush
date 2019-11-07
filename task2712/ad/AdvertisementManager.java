package com.javarush.task.task27.task2712.ad;

import com.javarush.task.task27.task2712.ConsoleHelper;

import java.io.IOException;
import java.text.CollationElementIterator;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class AdvertisementManager {
    private final AdvertisementStorage storage = AdvertisementStorage.getInstance();
    private int timeSeconds;

    public AdvertisementManager(int timeSeconds) {
        this.timeSeconds = timeSeconds;
    }

    public void processVideos() throws NoVideoAvailableException{
        if (storage.list().isEmpty() || getList().isEmpty()) throw new NoVideoAvailableException();

        for (Advertisement advertisement : bestList()) {
            System.out.println(advertisement);
            advertisement.revalidate();
        }
    }

    public List<Advertisement> getList() {
        return storage.list().stream()
                .filter(advertisement -> advertisement.getDuration() <= timeSeconds)
                .filter(advertisement -> advertisement.getHits() > 0)
                .collect(Collectors.toList());
    }

    private List<Advertisement> bestList(){
        List<List<Advertisement>> allCombinations = new ArrayList<>();
        makeAllSets(allCombinations, getList());



        if (allCombinations.size() != 1) allCombinations = filterByAmount(allCombinations);
        if (allCombinations.size() != 1) allCombinations = filterByDuration(allCombinations);

        if(allCombinations.size() != 1)  allCombinations = selectBestBetweenTwo(allCombinations);


        if (allCombinations.size() != 1) allCombinations.sort(Comparator.comparingInt(List::size));
        Collections.reverse(allCombinations);
        return sortResultList(allCombinations.get(0));
    }

    public List<Advertisement> sortResultList(List<Advertisement> resultList) {
            List<Advertisement> res = resultList.stream().sorted(Comparator
                    .comparingLong(Advertisement::getAmountPerOneDisplaying).reversed()
                    .thenComparingLong(Advertisement::getAmountPerOneSecondDisplaying))
                    .collect(Collectors.toList());
        return res;
    }

    public List<List<Advertisement>> selectBestBetweenTwo(List<List<Advertisement>> advertisement){
    for(int j = 0;j < advertisement.size()-1;j++){
                if( (calcAmoutPerDisplay(advertisement.get(j)) == calcAmoutPerDisplay(advertisement.get(j+1)) ) && ( calcDuration(advertisement.get(j)) == calcDuration(advertisement.get(j+1)) ) ){
                    if (calcHits(advertisement.get(j)) > calcHits(advertisement.get(j + 1))) {
                        advertisement.remove(advertisement.get(j));
                    } else {
                        advertisement.remove(advertisement.get(j + 1));
                    }
                }
            }

        return advertisement;
    }

    private  List<List<Advertisement>> filterByAmount(List<List<Advertisement>> list){
        long maxAmount = calcAmoutPerDisplay(list.stream()
                .max(Comparator.comparingLong(this::calcAmoutPerDisplay))
                .get());
        return list.stream().filter(
                advertisment  -> calcAmoutPerDisplay(advertisment) == maxAmount
        ).collect(Collectors.toList());
    }

    public List<List<Advertisement>> filterByDuration(List<List<Advertisement>> list) {
        int maxDuration = calcDuration(list.stream()
                .max(Comparator.comparingInt(this::calcDuration))
                .get());
        return list.stream()
                .filter(advertisment -> calcDuration(advertisment) == maxDuration)
                .collect(Collectors.toList());
    }

    private long calcAmoutPerDisplay(List<Advertisement> list){
     return list.stream().mapToLong(Advertisement::getAmountPerOneDisplaying).sum();
    }

    private int calcDuration(List<Advertisement> list){
        return list.stream().mapToInt(Advertisement::getDuration).sum();
    }

    private int calcHits(List<Advertisement> list){
        return list.stream().mapToInt(Advertisement::getHits).sum();
    }


    public void makeAllSets(List<List<Advertisement>> lists, List<Advertisement> list) {
        if((list.size() > 0 && !lists.contains(list)) && calcDuration(list) <= timeSeconds) lists.add(list);

        for (int i = 0; i < list.size(); i++) {
            List<Advertisement> newSet = new ArrayList<>(list);
            newSet.remove(i);
            makeAllSets(lists, newSet);
        }
    }









    /////

    private Advertisement maxPerOneDisplay(List<List<Advertisement>> lists){
        List< List<Advertisement>> max = lists;
        List<Advertisement> maxPerOneDisplay = Collections.emptyList();
        Collections.sort(max, new Comparator<List<Advertisement>>() {
                    @Override
                    public int compare(List<Advertisement> list, List<Advertisement> t1) {
                        return (int)(calcAmoutPerDisplay(list) - calcAmoutPerDisplay(t1));
                    }
                }
        );
        Collections.reverse(max);
        maxPerOneDisplay = max.get(0);

        Collections.sort(maxPerOneDisplay, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement advertisement, Advertisement t1) {
                return (int)(advertisement.getAmountPerOneDisplaying() - t1.getAmountPerOneDisplaying());
            }
        });
        Collections.reverse(maxPerOneDisplay);
        return maxPerOneDisplay.get(0);
    }

    private Advertisement maxDuration(List<List<Advertisement>> lists){
        List< List<Advertisement>> max = lists;
        List<Advertisement> maxDuration = Collections.emptyList();
        Collections.sort(max, new Comparator<List<Advertisement>>() {
                    @Override
                    public int compare(List<Advertisement> list, List<Advertisement> t1) {
                        return (int)(calcDuration(list) - calcDuration(t1));
                    }
                }
        );
        Collections.reverse(max);
        maxDuration = max.get(0);

        Collections.sort(maxDuration, new Comparator<Advertisement>() {
            @Override
            public int compare(Advertisement advertisement, Advertisement t1) {
                return (int)(advertisement.getDuration() - t1.getDuration());
            }
        });
        Collections.reverse(maxDuration);
        return maxDuration.get(0);
    }



}
