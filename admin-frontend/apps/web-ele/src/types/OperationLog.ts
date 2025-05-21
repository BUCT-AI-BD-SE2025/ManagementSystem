export interface OperationLog{
  logId: string;
  operatorId: string;
  targetType: string;
  targetId: string;
  actionType: string;
  oldValue: string;
  newValue: string;
  ipAddress: string;
  logTime: string;
}
