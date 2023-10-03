package com.kingyu.rlbird.rl.agent;

import com.kingyu.rlbird.rl.env.RlEnv;
import ai.djl.ndarray.NDList;
import ai.djl.training.tracker.Tracker;
import ai.djl.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ai.djl.modality.rl.agent.EpsilonGreedy} is a simple exploration/excitation agent.
 *
 * <p>
 * It helps other agents explore their environments during training by sometimes picking random
 * actions.
 *
 * <p>
 * If a model based agent is used, it will only explore paths through the environment that have
 * already been seen. While this is sometimes good, it is also important to sometimes explore
 * new paths as well. This agent exhibits a tradeoff that takes random paths a fixed percentage
 * of the time during training.
 */
public class EpsilonGreedy implements RlAgent{
  private final RlAgent baseAgent;
  private final Tracker exploreRate;
  private int counter;
  /**
   * Constructs an {@link ai.djl.modality.rl.agent.EpsilonGreedy}.
   *
   * @param baseAgent   the (presumably model-based) agent to use for exploitation and to train
   * @param exploreRate the probability of taking a random action
   */
  public EpsilonGreedy(RlAgent baseAgent,Tracker exploreRate) {
    this.baseAgent=baseAgent;
    this.exploreRate=exploreRate;
  }
  private static final Logger logger=LoggerFactory.getLogger(EpsilonGreedy.class);
  /**
   * {@inheritDoc}
   */
  @Override
  public NDList chooseAction(RlEnv env,boolean training) {
    if(training&&RandomUtils.random()<exploreRate.getNewValue(counter++)) {
      logger.info("***********RANDOM ACTION***********");
      return env.getActionSpace().randomAction();
    }else return baseAgent.chooseAction(env,training);
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void trainBatch(RlEnv.Step[] batchSteps) {
    baseAgent.trainBatch(batchSteps);
  }
}
