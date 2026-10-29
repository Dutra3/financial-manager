import { useQuery } from "@tanstack/react-query";
import { getMyProfile } from "../api/profileApi";
import { getUserEmail } from "../utils/auth";

export const useUserName = (): string => {
    const { data: profile } = useQuery({
        queryKey: ["profile", "me"],
        queryFn: getMyProfile,
        retry: false,
        staleTime: 5 * 60 * 1000,
    });

    if (profile?.name) {
        return profile.name.split(" ")[0];
    }

    const email = getUserEmail();
    if (email) {
        const local = email.split("@")[0];
        return local.charAt(0).toUpperCase() + local.slice(1);
    }

    return "você";
};
