export interface ContextItemDto {
  text: string;
  score: number;
  docId: string;
}

export interface FaqDto {
  id: string;
  question: string;
  answer: string;
  validated: boolean;
  autoGenerated: boolean;
  askedAt: string;
  contextItems: ContextItemDto[];
}