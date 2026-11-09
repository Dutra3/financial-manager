import { describe, it, expect, beforeEach } from "vitest";
import { getUserId, saveSession } from "./auth";

describe("auth utils", () => {
    beforeEach(() => localStorage.clear());

    it("returns the placeholder id when no session exists", () => {
        expect(getUserId()).toBe("00000000-0000-0000-0000-000000000000");
    });

    it("returns the stored user id after saveSession", () => {
        saveSession("token-x", "refresh-y", "user-123", "a@b.com");
        expect(getUserId()).toBe("user-123");
        expect(localStorage.getItem("token")).toBe("token-x");
        expect(localStorage.getItem("refreshToken")).toBe("refresh-y");
    });
});
