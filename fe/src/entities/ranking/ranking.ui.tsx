import { RankingDto } from "@/shared/api/ranking";
import { Input, Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/shared/ui";
import { useQuery } from "@tanstack/react-query";
import {
  ColumnDef,
  ColumnFiltersState,
  flexRender,
  getCoreRowModel,
  getFilteredRowModel,
  SortingState,
  useReactTable,
} from "@tanstack/react-table";
import { ChangeEvent, useEffect, useState } from "react";
import { RankingQueries } from "./ranking.queries";
import { Route } from "@/pages/_layout.ranking";
import { useNavigate } from "@tanstack/react-router";

const columns: ColumnDef<RankingDto>[] = [
  {
    accessorKey: "rank",
    header: "#",
  },
  {
    accessorKey: "userName",
    header: "UserName",
  },
  {
    accessorKey: "tier",
    header: "Tier",
  },
  {
    accessorKey: "lp",
    header: "LP",
  },
];

export function LeaderBoardList() {
  const { username } = Route.useSearch();
  const navigate = useNavigate({ from: Route.fullPath });

  const [columnFilters, setColumnFilters] = useState<ColumnFiltersState>([]);
  const [sorting, setSorting] = useState<SortingState>(() => []);
  const { data } = useQuery(RankingQueries.rankingQuery());

  const table = useReactTable({
    data,
    columns,
    onSortingChange: setSorting,
    getCoreRowModel: getCoreRowModel(),
    onColumnFiltersChange: setColumnFilters,
    getFilteredRowModel: getFilteredRowModel(),
    state: {
      sorting,
      columnFilters,
    },
  });
  const onSearch = (e: ChangeEvent<HTMLInputElement>) => {
    navigate({ search: { username: e.target.value } });
  };

  useEffect(() => {
    table.getColumn("userName")?.setFilterValue(username);
  }, [username]);

  return (
    <div className="w-full p-3 mb-nav">
      <div className="flex items-center py-4">
        <Input
          placeholder="Filter names..."
          value={(table.getColumn("userName")?.getFilterValue() as string) ?? ""}
          onChange={onSearch}
          className="max-w-sm"
        />
      </div>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id}>
                      {header.isPlaceholder
                        ? null
                        : flexRender(header.column.columnDef.header, header.getContext())}
                    </TableHead>
                  );
                })}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow key={row.id} data-state={row.getIsSelected() && "selected"}>
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className="h-24 text-center">
                  No results.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
