package com.kingyu.rlbird.rl;

import ai.djl.util.RandomUtils;
import com.kingyu.rlbird.rl.env.RlEnv;

import java.util.ArrayList;

/**
 * A simple {@link ReplayBuffer} that randomly selects across the whole buffer, but always
 * removes the oldest items in the buffer once it is full.
 */
public class LruReplayBuffer implements ReplayBuffer{
  private final int batchSize;
  private final RlEnv.Step[] steps;
  private final ArrayList<RlEnv.Step> stepToClose;
  private int firstStepIndex;
  private int stepsActualSize;
  /**
   * Constructs a {@link ai.djl.modality.rl.LruReplayBuffer}.
   *
   * @param batchSize  the number of steps to train on per batch
   * @param bufferSize the number of steps to hold in the buffer
   */
  public LruReplayBuffer(int batchSize,int bufferSize) {
    this.batchSize=batchSize;
    steps=new RlEnv.Step[bufferSize];
    stepToClose=new ArrayList<>(bufferSize);
    firstStepIndex=0;
    stepsActualSize=0;
  }
  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("PMD.AvoidArrayLoops")
  public RlEnv.Step[] getBatch() {
    RlEnv.Step[] batch=new RlEnv.Step[batchSize];
    for(int i=0;i<batchSize;i++) {
      int baseIndex=RandomUtils.nextInt(stepsActualSize);
      int index=Math.floorMod(firstStepIndex+baseIndex,steps.length);
      batch[i]=steps[index];
    }
    return batch;
  }
  /**
   * {@inheritDoc}
   */
  public void closeStep() {
    for(RlEnv.Step step:stepToClose) {
      step.close();
    }
    stepToClose.clear();
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void addStep(RlEnv.Step step) {
    if(stepsActualSize==steps.length) {
      int stepToReplace=Math.floorMod(firstStepIndex-1,steps.length);
      stepToClose.add(steps[stepToReplace]);
      steps[stepToReplace]=step;
      firstStepIndex=Math.floorMod(firstStepIndex+1,steps.length);
    }else {
      steps[stepsActualSize]=step;
      stepsActualSize++;
    }
  }
}
