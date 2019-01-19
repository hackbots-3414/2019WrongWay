package org.usfirst.frc.team3414.actuators;

import java.util.LinkedList;

public class Samplings {

	private LinkedList<Double> samples = new LinkedList<Double>();

    // Unit of sampling interval is millisecond 
    private long samplingIntervalMillis = 50;
    private int maxSamplingCount = 100;

    private long lastTimeStamp;
    private long currentTimeStamp;

    private double thresholdValue = 0.1;
    private long evaluateTimeMillis = 1000; // 1 second
    private long evaluateValueCount = 20;
    
    // check difference of every two consecutive values,
    // instead of check each value itself.
    // e.g., by check difference of encoder value, we can guess if motor is rotating or not.
    private boolean checkDifference = false;
    
    public Samplings()
    {
    	init();
    }

    public Samplings(boolean checkDifference)
    {
    	this.checkDifference = checkDifference;
    	init();
    }

    public Samplings(double thresholdValue, long evaluateTimeMillis, long samplingIntervalMillis, boolean checkDifference)
    {
    	this.thresholdValue = thresholdValue;
    	this.evaluateTimeMillis = evaluateTimeMillis;
    	this.samplingIntervalMillis = samplingIntervalMillis;
    	this.checkDifference = checkDifference;
    	
    	init();
    }

    private void init()
    {
    	// calculates EvaluateValueCount
    	evaluateValueCount = evaluateTimeMillis / samplingIntervalMillis;
    	if(evaluateValueCount < 1)
    		evaluateValueCount = 1;
    }

    public void addSample(double sampleValue)
    {
    	currentTimeStamp = System.currentTimeMillis();

    	if(samples.size() == maxSamplingCount)
    		samples.removeFirst();

    	// if the new sample isn't the first one in the list,
    	// then check sampling recording interval;
    	// skip some samples if outside sampling frequency is faster than 
    	// sampling  (recording or memorizing) in this class
    	if(samples.size() == 0)
    	{
	    	samples.add((Double) sampleValue);
	    	lastTimeStamp = currentTimeStamp;
    	}
    	else
    	{
    		if(currentTimeStamp >= lastTimeStamp + samplingIntervalMillis)
    		{
    			samples.addLast((Double) sampleValue);
    	    	lastTimeStamp = currentTimeStamp;
    		}
    	}

    }

    public boolean sampleValuesAreSmall()
    {
    	return valuesOrDifferencesAreSmall(false);
    }

    public boolean sampleValueChangesAreSmall()
    {
    	return valuesOrDifferencesAreSmall(true);
    }

    public boolean valuesOrDifferencesAreSmall(boolean checkDif)
    {
    	checkDifference = checkDif;
    	
    	// evaluates last couple of samples
    	// to see if they are all smaller than the threshold
    	long iLoop, iStart, iEnd;
    	
    	if(samples.size() == 0) 
    	{
    		// To do: handle this error
    		// System.out.println("There is no sample data collected yet.");
    		return false;
    	}
    	else if(samples.size() < evaluateValueCount + 1)
    	{
    		// very few samples
    		iStart = 0;
    		iEnd = samples.size() - 1;
    		return false;
    	}
    	else
    	{
    		iStart = samples.size() - evaluateValueCount;
    		iEnd = samples.size() - 1;
    	}
    	
		double previousSampleValue = 0;
		double currentSampleValue;
		double difference;
    	for(iLoop = iStart; iLoop <= iEnd; iLoop++)
    	{
    		currentSampleValue = samples.get((int)iLoop);
    		
    		// even one sample value or difference is bigger than the threshold, will be safe
    		if(checkDifference)
    		{
    			if((iLoop == iStart) && (iStart > 0))
    				previousSampleValue = samples.get((int)(iLoop - 1));
    			
    			difference = currentSampleValue - previousSampleValue;
//       System.out.println(iLoop + " difference = " + difference);
        		if(Math.abs(difference) > thresholdValue)
        			return false;
        		previousSampleValue = currentSampleValue;
    		}
    		else
    		{
    			if(Math.abs(currentSampleValue) > thresholdValue)
    				return false;
    		}
    	}
    	
    	// all last couple of samples are smaller than the threshold.
    	return true;
    }
    
    public void reset()
    {
    	samples.clear();
    }
    
    public void displaySamples()
    {
    	int sampleSize = samples.size(); 
    	for(int i = 0; i < sampleSize; i++)
    		System.out.println(i + " = " + samples.get(i));
    }

}