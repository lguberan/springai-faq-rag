import {useEffect, useState} from "react";
import {Box, Button, Flex, Heading, Image, Progress, Separator, Stack, Text, Textarea,} from "@chakra-ui/react";
import axios from "axios";
import {BeatLoader} from "react-spinners"
import {FaqDto} from "../types/FaqDto";

const apiUrl = import.meta.env.VITE_API_URL || "";

const AskPage = () => {
    const [question, setQuestion] = useState("");
    const [faqResponse, setFaqResponse] = useState<FaqDto | null>(null);
    const [recentQuestions, setRecentQuestions] = useState<FaqDto[]>([]);
    const [loading, setLoading] = useState(false);

    const handleAsk = async () => {
        try {
            setLoading(true);
            const response = await axios.get<FaqDto>(`${apiUrl}/faq/ask`, {
                params: {question},
            });
            setFaqResponse(response.data);
        } catch (error) {
            console.error("Error fetching answer:", error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        axios
            .get<FaqDto[]>(`${apiUrl}/faq?validated=true`)
            .then((res) => setRecentQuestions(res.data.slice(-20).reverse()))
            .catch((err) => console.error("Failed to fetch recent questions", err));
    }, []);

    return (
        <Flex direction="column" align="center" mt={10}>
            <Box width="100%" maxW="800px" p={4} textAlign="left">
                <Flex align="center" mb={6}>
                    <Image src="/ai-faq-image.png" alt="AI Assistant" height="90px" mr={4}/>
                    <Box>
                        <Heading as="h2" size="lg">
                            AI-assisted FAQ
                        </Heading>
                        <Text fontSize="md" color="gray.600">
                            for any AI-powered FAQ system
                        </Text>
                    </Box>
                </Flex>

                <Textarea
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    placeholder="Type your question here..."
                    size="lg"
                    height="120px"
                    mb={4}
                    fontSize="lg"
                />
                <Button
                    onClick={handleAsk}
                    disabled={!question.trim() || loading}
                    colorPalette="teal"
                    loading={loading}
                    // loadingText="Thinking..."
                    width="fit-content"
                    spinner={<BeatLoader size={8} color="white"/>}
                >
                    Submit
                </Button>
            </Box>

            {faqResponse && (
                <Box mt={6} width="100%" maxW="780px" p={4} borderWidth={1} borderRadius="md">
                    <Heading as="h3" size="md" mb={2}>
                        💬 AI Answer
                    </Heading>
                    <Text fontSize="lg" mb={6}>
                        {faqResponse.answer}
                    </Text>

                    {faqResponse.contextItems && faqResponse.contextItems.length > 0 && (
                        <>
                            <Separator mb={4}/>
                            <Text fontSize="sm" fontWeight="semibold" color="gray.600" mb={3}>
                                Context Items:
                            </Text>
                            {faqResponse.contextItems.map((item, index) => (
                                <Box key={index} mb={3} p={2} borderWidth="1px" borderRadius="md" bg="gray.50">
                                    <Flex justify="space-between" align="center" mb={1}>
                                        <Text fontSize="sm" fontWeight="bold">
                                            {index + 1}
                                        </Text>
                                        <Flex align="center" gap={2}>
                                            <Progress.Root value={item.score * 100} max={100} width="150px">
                                                <Progress.Track>
                                                    <Progress.Range colorPalette="teal"/>
                                                </Progress.Track>
                                            </Progress.Root>
                                            <Text fontSize="xs" color="teal.700">
                                                {(item.score * 100).toFixed(2)}%
                                            </Text>
                                        </Flex>
                                    </Flex>
                                    <Text fontSize="sm" color="teal.700">
                                        {item.text}
                                    </Text>
                                </Box>
                            ))}
                        </>
                    )}
                </Box>
            )}

            <Box mt={10} width="100%" maxW="780px" p={4} borderWidth={1} borderRadius="md">
                <Heading as="h3" size="md" mb={2}>
                    📜 Recent Validated Questions
                </Heading>
                <Stack gap={3}>
                    {recentQuestions.map((q) => (
                        <Box key={q.id} borderWidth="1px" borderRadius="md" p={3} bg="gray.50">
                            <Text fontWeight="bold" fontSize="sm">
                                {q.question}
                            </Text>
                            <Text fontSize="xs" color="gray.600" mt={1}>
                                {q.answer}
                            </Text>
                        </Box>
                    ))}
                </Stack>
            </Box>
        </Flex>
    );
};

export default AskPage;