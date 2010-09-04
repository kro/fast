package fast.bats.europe.templates;

import fast.templates.SequenceTemplate;

public class TradeBreak extends SequenceTemplate {
  public static final TradeBreak TEMPLATE = new TradeBreak();
  
  private TradeBreak() {
    add(ExecutionId.TEMPLATE);
  }
}
